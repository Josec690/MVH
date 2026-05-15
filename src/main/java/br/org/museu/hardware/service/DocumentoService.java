package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Documento;
import br.org.museu.hardware.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository repository;

    public List<Documento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Documento> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return repository.findById(id);
    }

    public List<Documento> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento inválido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Documento salvar(Documento documento) {
        validar(documento);
        return repository.save(documento);
    }

    public Documento atualizar(Long id, Documento documento) {
        Optional<Documento> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Documento não encontrado com ID: " + id);
        }

        Documento doc = existente.get();
        if (documento.getTitulo() != null) {
            doc.setTitulo(documento.getTitulo());
        }
        if (documento.getUrl() != null) {
            doc.setUrl(documento.getUrl());
        }

        return repository.save(doc);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        repository.deleteById(id);
    }

    private void validar(Documento documento) {
        if (documento == null) {
            throw new IllegalArgumentException("Documento não pode ser nulo");
        }
        if (documento.getTitulo() == null || documento.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do documento é obrigatório");
        }
        if (documento.getUrl() == null || documento.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL do documento é obrigatória");
        }
        if (documento.getEquipamento() == null) {
            throw new IllegalArgumentException("Equipamento é obrigatório");
        }
    }
}
