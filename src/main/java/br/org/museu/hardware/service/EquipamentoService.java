package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository repository;

    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Equipamento> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Equipamento> listarPorFabricante(Long idFabricante) {
        if (idFabricante == null || idFabricante <= 0) {
            throw new IllegalArgumentException("ID do fabricante inválido");
        }
        return repository.findByFabricante_IdFabricante(idFabricante);
    }

    public List<Equipamento> listarPorGeracao(String geracao) {
        if (geracao == null || geracao.trim().isEmpty()) {
            throw new IllegalArgumentException("Geração não pode estar vazia");
        }
        return repository.findByGeracao(geracao);
    }

    public Equipamento salvar(Equipamento equipamento) {
        validar(equipamento);
        return repository.save(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento equipamento) {
        Optional<Equipamento> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Equipamento não encontrado com ID: " + id);
        }

        Equipamento eq = existente.get();
        if (equipamento.getModelo() != null) {
            eq.setModelo(equipamento.getModelo());
        }
        if (equipamento.getAno() > 0) {
            eq.setAno(equipamento.getAno());
        }
        if (equipamento.getGeracao() != null) {
            eq.setGeracao(equipamento.getGeracao());
        }
        if (equipamento.getFabricante() != null) {
            eq.setFabricante(equipamento.getFabricante());
        }

        return repository.save(eq);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Equipamento equipamento) {
        if (equipamento == null) {
            throw new IllegalArgumentException("Equipamento não pode ser nulo");
        }
        if (equipamento.getModelo() == null || equipamento.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo é obrigatório");
        }
        if (equipamento.getAno() <= 0) {
            throw new IllegalArgumentException("Ano deve ser maior que 0");
        }
        if (equipamento.getGeracao() == null || equipamento.getGeracao().trim().isEmpty()) {
            throw new IllegalArgumentException("Geração é obrigatória");
        }
        if (equipamento.getFabricante() == null) {
            throw new IllegalArgumentException("Fabricante é obrigatório");
        }
    }
}

