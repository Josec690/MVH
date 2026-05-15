package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Fabricante;
import br.org.museu.hardware.service.FabricanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fabricantes")
@CrossOrigin(origins = "*")
public class FabricanteController {

    @Autowired
    private FabricanteService service;

    @GetMapping
    public ResponseEntity<List<Fabricante>> listarTodos() {
        List<Fabricante> fabricantes = service.listarTodos();
        return ResponseEntity.ok(fabricantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fabricante> obterPorId(@PathVariable Long id) {
        Optional<Fabricante> fabricante = service.obterPorId(id);
        return fabricante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Fabricante>> listarPorPais(@PathVariable String pais) {
        List<Fabricante> fabricantes = service.listarPorPais(pais);
        return ResponseEntity.ok(fabricantes);
    }

    @PostMapping
    public ResponseEntity<Fabricante> criar(@RequestBody Fabricante fabricante) {
        try {
            Fabricante criado = service.salvar(fabricante);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fabricante> atualizar(@PathVariable Long id, @RequestBody Fabricante fabricante) {
        try {
            Fabricante atualizado = service.atualizar(id, fabricante);
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
