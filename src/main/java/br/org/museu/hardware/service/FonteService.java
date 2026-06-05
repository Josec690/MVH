package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.model.Fonte;
import br.org.museu.hardware.repository.EquipamentoRepository;
import br.org.museu.hardware.repository.FonteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FonteService {

    @Autowired
    private FonteRepository repository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public List<Fonte> listarTodas() {
        return repository.findAll();
    }

    public Optional<Fonte> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        return repository.findById(id);
    }

    public List<Fonte> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento invalido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Fonte salvar(Fonte fonte) {
        validar(fonte);
        return repository.save(fonte);
    }

    public Fonte salvar(Fonte fonte, Long idEquipamento) {
        fonte.setEquipamento(obterEquipamento(idEquipamento));
        validar(fonte);
        return repository.save(fonte);
    }

    public Fonte atualizar(Long id, Fonte fonte) {
        return atualizar(id, fonte, null);
    }

    public Fonte atualizar(Long id, Fonte fonte, Long idEquipamento) {
        Optional<Fonte> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Fonte nao encontrada com ID: " + id);
        }

        Fonte f = existente.get();
        if (fonte.getNome_site() != null) {
            f.setNome_site(fonte.getNome_site());
        }
        if (fonte.getUrl_original() != null) {
            f.setUrl_original(fonte.getUrl_original());
        }
        if (idEquipamento != null) {
            f.setEquipamento(obterEquipamento(idEquipamento));
        }

        validar(f);
        return repository.save(f);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        repository.deleteById(id);
    }

    private void validar(Fonte fonte) {
        if (fonte == null) {
            throw new IllegalArgumentException("Fonte nao pode ser nula");
        }
        if (fonte.getNome_site() == null || fonte.getNome_site().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do site e obrigatorio");
        }
        if (fonte.getUrl_original() == null || fonte.getUrl_original().trim().isEmpty()) {
            throw new IllegalArgumentException("URL e obrigatoria");
        }
        if (fonte.getEquipamento() == null) {
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
