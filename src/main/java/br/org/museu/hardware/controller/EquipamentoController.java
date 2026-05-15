package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipamentos")
@CrossOrigin(origins = "*")
public class EquipamentoController {

    @Autowired
    private EquipamentoService service;

    @GetMapping
    public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = service.listarTodos();
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamento> obterPorId(@PathVariable Long id) {
        Optional<Equipamento> equipamento = service.obterPorId(id);
        return equipamento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fabricante/{idFabricante}")
    public ResponseEntity<List<Equipamento>> listarPorFabricante(@PathVariable Long idFabricante) {
        List<Equipamento> equipamentos = service.listarPorFabricante(idFabricante);
        return ResponseEntity.ok(equipamentos);
    }

    @GetMapping("/geracao/{geracao}")
    public ResponseEntity<List<Equipamento>> listarPorGeracao(@PathVariable String geracao) {
        List<Equipamento> equipamentos = service.listarPorGeracao(geracao);
        return ResponseEntity.ok(equipamentos);
    }

    @PostMapping
    public ResponseEntity<Equipamento> criar(@RequestBody Equipamento equipamento) {
        try {
            Equipamento criado = service.salvar(equipamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> atualizar(@PathVariable Long id, @RequestBody Equipamento equipamento) {
        try {
            Equipamento atualizado = service.atualizar(id, equipamento);
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
