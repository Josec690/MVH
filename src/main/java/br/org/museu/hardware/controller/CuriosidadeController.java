package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Curiosidade;
import br.org.museu.hardware.service.CuriosidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curiosidades")
@CrossOrigin(origins = "*")
public class CuriosidadeController {

    @Autowired
    private CuriosidadeService service;

    @GetMapping
    public ResponseEntity<List<Curiosidade>> listarTodas() {
        List<Curiosidade> curiosidades = service.listarTodas();
        return ResponseEntity.ok(curiosidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curiosidade> obterPorId(@PathVariable Long id) {
        Optional<Curiosidade> curiosidade = service.obterPorId(id);
        return curiosidade.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<Curiosidade>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Curiosidade> curiosidades = service.listarPorEquipamento(idEquipamento);
        return ResponseEntity.ok(curiosidades);
    }

    @PostMapping
    public ResponseEntity<Curiosidade> criar(@RequestBody Curiosidade curiosidade) {
        try {
            Curiosidade criada = service.salvar(curiosidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curiosidade> atualizar(@PathVariable Long id, @RequestBody Curiosidade curiosidade) {
        try {
            Curiosidade atualizada = service.atualizar(id, curiosidade);
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
