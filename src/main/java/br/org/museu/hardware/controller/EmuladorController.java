package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Emulador;
import br.org.museu.hardware.service.EmuladorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emuladores")
@CrossOrigin(origins = "*")
public class EmuladorController {

    @Autowired
    private EmuladorService service;

    @GetMapping
    public ResponseEntity<List<Emulador>> listarTodos() {
        List<Emulador> emuladores = service.listarTodos();
        return ResponseEntity.ok(emuladores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emulador> obterPorId(@PathVariable Long id) {
        Optional<Emulador> emulador = service.obterPorId(id);
        return emulador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fabricante/{idFabricante}")
    public ResponseEntity<List<Emulador>> listarPorFabricante(@PathVariable Long idFabricante) {
        List<Emulador> emuladores = service.listarPorFabricante(idFabricante);
        return ResponseEntity.ok(emuladores);
    }

    @PostMapping
    public ResponseEntity<Emulador> criar(@RequestBody Emulador emulador) {
        try {
            Emulador criado = service.salvar(emulador);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emulador> atualizar(@PathVariable Long id, @RequestBody Emulador emulador) {
        try {
            Emulador atualizado = service.atualizar(id, emulador);
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
