package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Curiosidade;
import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.repository.CuriosidadeRepository;
import br.org.museu.hardware.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuriosidadeService {

    @Autowired
    private CuriosidadeRepository repository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public List<Curiosidade> listarTodas() {
        return repository.findAll();
    }

    public Optional<Curiosidade> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        return repository.findById(id);
    }

    public List<Curiosidade> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento invalido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Curiosidade salvar(Curiosidade curiosidade) {
        validar(curiosidade);
        return repository.save(curiosidade);
    }

    public Curiosidade salvar(Curiosidade curiosidade, Long idEquipamento) {
        curiosidade.setEquipamento(obterEquipamento(idEquipamento));
        validar(curiosidade);
        return repository.save(curiosidade);
    }

    public Curiosidade atualizar(Long id, Curiosidade curiosidade) {
        return atualizar(id, curiosidade, null);
    }

    public Curiosidade atualizar(Long id, Curiosidade curiosidade, Long idEquipamento) {
        Optional<Curiosidade> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Curiosidade nao encontrada com ID: " + id);
        }

        Curiosidade cur = existente.get();
        if (curiosidade.getDescricao() != null) {
            cur.setDescricao(curiosidade.getDescricao());
        }
        if (idEquipamento != null) {
            cur.setEquipamento(obterEquipamento(idEquipamento));
        }

        validar(cur);
        return repository.save(cur);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        repository.deleteById(id);
    }

    private void validar(Curiosidade curiosidade) {
        if (curiosidade == null) {
            throw new IllegalArgumentException("Curiosidade nao pode ser nula");
        }
        if (curiosidade.getDescricao() == null || curiosidade.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descricao e obrigatoria");
        }
        if (curiosidade.getEquipamento() == null) {
            throw new IllegalArgumentException("Equipamento e obrigatorio");
        }
    }

    private Equipamento obterEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento invalido");
        }
        return equipamentoRepository.findById(idEquipamento)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento nao encontrado com ID: " + idEquipamento));
    }
}
