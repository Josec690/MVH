package br.org.museu.hardware.controller;

import br.org.museu.hardware.dto.FabricanteDTO;
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
    public ResponseEntity<List<FabricanteDTO>> listarTodos() {
        List<Fabricante> fabricantes = service.listarTodos();
        return ResponseEntity.ok(service.toDTOs(fabricantes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FabricanteDTO> obterPorId(@PathVariable Long id) {
        Optional<Fabricante> fabricante = service.obterPorId(id);
        return fabricante.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pais/{pais}")
public ResponseEntity<List<FabricanteDTO>> listarPorPais(@PathVariable String pais) {
        List<Fabricante> fabricantes = service.listarPorPais(pais);
        return ResponseEntity.ok(service.toDTOs(fabricantes));
    }

    @PostMapping
public ResponseEntity<FabricanteDTO> criar(@RequestBody Fabricante fabricante) {
        try {
            Fabricante criado = service.salvar(fabricante);
            FabricanteDTO dto = new FabricanteDTO();
            dto.setId_fabricante(criado.getIdFabricante());
            dto.setNome(criado.getNome());
            dto.setPais(criado.getPais());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
public ResponseEntity<FabricanteDTO> atualizar(@PathVariable Long id, @RequestBody Fabricante fabricante) {
        try {
            Fabricante atualizado = service.atualizar(id, fabricante);
            FabricanteDTO dto = new FabricanteDTO();
            dto.setId_fabricante(atualizado.getIdFabricante());
            dto.setNome(atualizado.getNome());
            dto.setPais(atualizado.getPais());
            return ResponseEntity.ok(dto);
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
