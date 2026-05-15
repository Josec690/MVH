package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Curiosidade;
import br.org.museu.hardware.repository.CuriosidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuriosidadeService {

    @Autowired
    private CuriosidadeRepository repository;

    public List<Curiosidade> listarTodas() {
        return repository.findAll();
    }

    public Optional<Curiosidade> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Curiosidade> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento inválido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Curiosidade salvar(Curiosidade curiosidade) {
        validar(curiosidade);
        return repository.save(curiosidade);
    }

    public Curiosidade atualizar(Long id, Curiosidade curiosidade) {
        Optional<Curiosidade> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Curiosidade não encontrada com ID: " + id);
        }

        Curiosidade cur = existente.get();
        if (curiosidade.getDescricao() != null) {
            cur.setDescricao(curiosidade.getDescricao());
        }

        return repository.save(cur);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Curiosidade curiosidade) {
        if (curiosidade == null) {
            throw new IllegalArgumentException("Curiosidade não pode ser nula");
        }
        if (curiosidade.getDescricao() == null || curiosidade.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (curiosidade.getEquipamento() == null) {
            throw new IllegalArgumentException("Equipamento é obrigatório");
        }
    }
}
