package br.org.museu.hardware.service;

import br.org.museu.hardware.model.Documento;
import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.repository.DocumentoRepository;
import br.org.museu.hardware.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository repository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public List<Documento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Documento> obterPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        return repository.findById(id);
    }

    public List<Documento> listarPorEquipamento(Long idEquipamento) {
        if (idEquipamento == null || idEquipamento <= 0) {
            throw new IllegalArgumentException("ID do equipamento invalido");
        }
        return repository.findByEquipamento_IdEquipamento(idEquipamento);
    }

    public Documento salvar(Documento documento) {
        validar(documento);
        return repository.save(documento);
    }

    public Documento salvar(Documento documento, Long idEquipamento) {
        documento.setEquipamento(obterEquipamento(idEquipamento));
        validar(documento);
        return repository.save(documento);
    }

    public Documento atualizar(Long id, Documento documento) {
        return atualizar(id, documento, null);
    }

    public Documento atualizar(Long id, Documento documento, Long idEquipamento) {
        Optional<Documento> existente = repository.findById(id);
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Documento nao encontrado com ID: " + id);
        }

        Documento doc = existente.get();
        if (documento.getTitulo() != null) {
            doc.setTitulo(documento.getTitulo());
        }
        if (documento.getUrl() != null) {
            doc.setUrl(documento.getUrl());
        }
        if (idEquipamento != null) {
            doc.setEquipamento(obterEquipamento(idEquipamento));
        }

        validar(doc);
        return repository.save(doc);
    }

    public void deletar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalido");
        }
        repository.deleteById(id);
    }

    private void validar(Documento documento) {
        if (documento == null) {
            throw new IllegalArgumentException("Documento nao pode ser nulo");
        }
        if (documento.getTitulo() == null || documento.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo do documento e obrigatorio");
        }
        if (documento.getUrl() == null || documento.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("URL do documento e obrigatoria");
        }
        if (documento.getEquipamento() == null) {
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
