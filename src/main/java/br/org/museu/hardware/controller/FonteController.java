package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Fonte;
import br.org.museu.hardware.service.FonteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fontes")
@CrossOrigin(origins = "*")
public class FonteController {

    @Autowired
    private FonteService service;

    @GetMapping
    public ResponseEntity<List<Fonte>> listarTodas() {
        List<Fonte> fontes = service.listarTodas();
        return ResponseEntity.ok(fontes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fonte> obterPorId(@PathVariable Long id) {
        Optional<Fonte> fonte = service.obterPorId(id);
        return fonte.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<Fonte>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Fonte> fontes = service.listarPorEquipamento(idEquipamento);
        return ResponseEntity.ok(fontes);
    }

    @PostMapping
    public ResponseEntity<Fonte> criar(@RequestBody Fonte fonte) {
        try {
            Fonte criada = service.salvar(fonte);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fonte> atualizar(@PathVariable Long id, @RequestBody Fonte fonte) {
        try {
            Fonte atualizada = service.atualizar(id, fonte);
            return ResponseEntity.ok(atualizada);
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
