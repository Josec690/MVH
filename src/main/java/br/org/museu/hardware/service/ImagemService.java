package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.model.Imagem;
import br.org.museu.hardware.repository.EquipamentoRepository;
import br.org.museu.hardware.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository repository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public List<Imagem> listarTodas() {
        return repository.findAll();
    }

    public Optional<Imagem> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        return repository.findById(id);
    }

    public List<Imagem> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento invalido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Imagem salvar(Imagem imagem) {
        validar(imagem);
        return repository.save(imagem);
    }

    public Imagem salvar(Imagem imagem, Long idEquipamento) {
        imagem.setEquipamento(obterEquipamento(idEquipamento));
        validar(imagem);
        return repository.save(imagem);
    }

    public Imagem atualizar(Long id, Imagem imagem) {
        return atualizar(id, imagem, null);
    }

    public Imagem atualizar(Long id, Imagem imagem, Long idEquipamento) {
        Optional<Imagem> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Imagem nao encontrada com ID: " + id);
        }

        Imagem img = existente.get();
        if (imagem.getFormato() != null) {
            img.setFormato(imagem.getFormato());
        }
        if (imagem.getTamanho() != null) {
            img.setTamanho(imagem.getTamanho());
        }
        if (imagem.getResolucao() != null) {
            img.setResolucao(imagem.getResolucao());
        }
        if (imagem.getUrl() != null) {
            img.setUrl(imagem.getUrl());
        }
        if (idEquipamento != null) {
            img.setEquipamento(obterEquipamento(idEquipamento));
        }

        validar(img);
        return repository.save(img);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        repository.deleteById(id);
    }

    private void validar(Imagem imagem) {
        if (imagem == null) {
            throw new IllegalArgumentException("Imagem nao pode ser nula");
        }
        if (imagem.getFormato() == null || imagem.getFormato().trim().isEmpty()) {
            throw new IllegalArgumentException("Formato da imagem e obrigatorio");
        }
        if (imagem.getEquipamento() == null) {
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
