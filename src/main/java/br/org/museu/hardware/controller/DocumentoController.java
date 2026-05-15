package br.org.museu.hardware.controller;

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
public class DocumentoController {

    @Autowired
    private DocumentoService service;

    @GetMapping
    public ResponseEntity<List<Documento>> listarTodos() {
        List<Documento> documentos = service.listarTodos();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obterPorId(@PathVariable Long id) {
        Optional<Documento> documento = service.obterPorId(id);
        return documento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<Documento>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Documento> documentos = service.listarPorEquipamento(idEquipamento);
        return ResponseEntity.ok(documentos);
    }

    @PostMapping
    public ResponseEntity<Documento> criar(@RequestBody Documento documento) {
        try {
            Documento criado = service.salvar(documento);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizar(@PathVariable Long id, @RequestBody Documento documento) {
        try {
            Documento atualizado = service.atualizar(id, documento);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
