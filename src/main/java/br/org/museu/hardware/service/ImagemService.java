package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Imagem;
import br.org.museu.hardware.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository repository;

    public List<Imagem> listarTodas() {
        return repository.findAll();
    }

    public Optional<Imagem> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Imagem> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento inválido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Imagem salvar(Imagem imagem) {
        validar(imagem);
        return repository.save(imagem);
    }

    public Imagem atualizar(Long id, Imagem imagem) {
        Optional<Imagem> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Imagem não encontrada com ID: " + id);
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

        return repository.save(img);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Imagem imagem) {
        if (imagem == null) {
            throw new IllegalArgumentException("Imagem não pode ser nula");
        }
        if (imagem.getFormato() == null || imagem.getFormato().trim().isEmpty()) {
            throw new IllegalArgumentException("Formato da imagem é obrigatório");
        }
        if (imagem.getEquipamento() == null) {
            throw new IllegalArgumentException("Equipamento é obrigatório");
        }
    }
}
