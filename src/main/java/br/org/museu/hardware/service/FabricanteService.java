package br.org.museu.hardware.service;

import br.org.museu.hardware.dto.FabricanteDTO;
import br.org.museu.hardware.model.Fabricante;
import br.org.museu.hardware.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FabricanteService {

    @Autowired
    private FabricanteRepository repository;

    public List<Fabricante> listarTodos() {
        return repository.findAll();
    }

    public Optional<Fabricante> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Fabricante> listarPorPais(String pais) {
        if (pais == null || pais.trim().isEmpty()) {
            throw new IllegalArgumentException("País não pode estar vazio");
        }
        return repository.findByPaisContainingIgnoreCase(pais);
    }

    public Fabricante salvar(Fabricante fabricante) {
        validar(fabricante);
        return repository.save(fabricante);
    }

    public Fabricante atualizar(Long id, Fabricante fabricante) {
        Optional<Fabricante> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Fabricante não encontrado com ID: " + id);
        }

        Fabricante fab = existente.get();
        if (fabricante.getNome() != null) {
            fab.setNome(fabricante.getNome());
        }
        if (fabricante.getPais() != null) {
            fab.setPais(fabricante.getPais());
        }

        return repository.save(fab);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Fabricante fabricante) {
        if (fabricante == null) {
            throw new IllegalArgumentException("Fabricante não pode ser nulo");
        }
        if (fabricante.getNome() == null || fabricante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do fabricante é obrigatório");
        }
        if (fabricante.getPais() == null || fabricante.getPais().trim().isEmpty()) {
            throw new IllegalArgumentException("País do fabricante é obrigatório");
        }
    }

    public List<FabricanteDTO> toDTOs(List<Fabricante> fabricantes) {
        if (fabricantes == null) {
            return List.of();
        }

        return fabricantes.stream().map(f -> {
            FabricanteDTO dto = new FabricanteDTO();
            dto.setId_fabricante(f.getIdFabricante());
            dto.setNome(f.getNome());
            dto.setPais(f.getPais());
            return dto;
        }).collect(Collectors.toList());
    }
}

