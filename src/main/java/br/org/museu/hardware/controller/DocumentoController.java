package br.org.museu.hardware.controller;

import br.org.museu.hardware.dto.DocumentoDTO;
import br.org.museu.hardware.model.Documento;
import br.org.museu.hardware.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class    DocumentoController {

    @Autowired
    private DocumentoService service;

    @GetMapping
    public ResponseEntity<List<DocumentoDTO>> listarTodos() {
        List<DocumentoDTO> dtos = service.listarTodos().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> obterPorId(@PathVariable Long id) {
        Optional<Documento> documento = service.obterPorId(id);
        return documento.map(d -> ResponseEntity.ok(toDTO(d)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<DocumentoDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<DocumentoDTO> dtos = service.listarPorEquipamento(idEquipamento).stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<DocumentoDTO> criar(@RequestBody DocumentoDTO dto) {
        try {
            Documento criado = service.salvar(toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(criado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoDTO> atualizar(@PathVariable Long id, @RequestBody DocumentoDTO dto) {
        try {
            Documento atualizado = service.atualizar(id, toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.ok(toDTO(atualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DocumentoDTO toDTO(Documento documento) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId_doc(documento.getIdDoc());
        dto.setTitulo(documento.getTitulo());
        dto.setUrl(documento.getUrl());
        if (documento.getEquipamento() != null) {
            dto.setIdEquipamento(documento.getEquipamento().getIdEquipamento());
        }
        return dto;
    }

    private Documento toEntity(DocumentoDTO dto) {
        Documento documento = new Documento();
        documento.setTitulo(dto.getTitulo());
        documento.setUrl(dto.getUrl());
        return documento;
    }
}
