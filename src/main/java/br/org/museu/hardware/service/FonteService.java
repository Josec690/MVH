package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Fonte;
import br.org.museu.hardware.repository.FonteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FonteService {

    @Autowired
    private FonteRepository repository;

    public List<Fonte> listarTodas() {
        return repository.findAll();
    }

    public Optional<Fonte> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Fonte> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento inválido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Fonte salvar(Fonte fonte) {
        validar(fonte);
        return repository.save(fonte);
    }

    public Fonte atualizar(Long id, Fonte fonte) {
        Optional<Fonte> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Fonte não encontrada com ID: " + id);
        }

        Fonte f = existente.get();
        if (fonte.getNome_site() != null) {
            f.setNome_site(fonte.getNome_site());
        }
        if (fonte.getUrl_original() != null) {
            f.setUrl_original(fonte.getUrl_original());
        }

        return repository.save(f);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Fonte fonte) {
        if (fonte == null) {
            throw new IllegalArgumentException("Fonte não pode ser nula");
        }
        if (fonte.getNome_site() == null || fonte.getNome_site().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do site é obrigatório");
        }
        if (fonte.getUrl_original() == null || fonte.getUrl_original().trim().isEmpty()) {
            throw new IllegalArgumentException("URL é obrigatória");
        }
        if (fonte.getEquipamento() == null) {
            throw new IllegalArgumentException("Equipamento é obrigatório");
        }
    }
}
