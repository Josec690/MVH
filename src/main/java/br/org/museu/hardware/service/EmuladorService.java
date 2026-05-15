package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Emulador;
import br.org.museu.hardware.repository.EmuladorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmuladorService {

    @Autowired
    private EmuladorRepository repository;

    public List<Emulador> listarTodos() {
        return repository.findAll();
    }

    public Optional<Emulador> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Emulador> listarPorFabricante(Long idFabricante) {
        if (idFabricante == null || idFabricante <= 0) {
            throw new IllegalArgumentException("ID do fabricante inválido");
        }
        return repository.findByFabricante_IdFabricante(idFabricante);
    }

    public Emulador salvar(Emulador emulador) {
        validar(emulador);
        return repository.save(emulador);
    }

    public Emulador atualizar(Long id, Emulador emulador) {
        Optional<Emulador> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Emulador não encontrado com ID: " + id);
        }

        Emulador emu = existente.get();
        if (emulador.getNome_emu() != null) {
            emu.setNome_emu(emulador.getNome_emu());
        }
        if (emulador.getVersao() != null) {
            emu.setVersao(emulador.getVersao());
        }
        if (emulador.getFabricante() != null) {
            emu.setFabricante(emulador.getFabricante());
        }

        return repository.save(emu);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Emulador emulador) {
        if (emulador == null) {
            throw new IllegalArgumentException("Emulador não pode ser nulo");
        }
        if (emulador.getNome_emu() == null || emulador.getNome_emu().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do emulador é obrigatório");
        }
        if (emulador.getVersao() == null || emulador.getVersao().trim().isEmpty()) {
            throw new IllegalArgumentException("Versão do emulador é obrigatória");
        }
        if (emulador.getFabricante() == null) {
            throw new IllegalArgumentException("Fabricante é obrigatório");
        }
    }
}
