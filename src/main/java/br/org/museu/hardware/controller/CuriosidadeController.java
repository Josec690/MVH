package br.org.museu.hardware.controller;

import br.org.museu.hardware.dto.CuriosidadeDTO;
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
    public ResponseEntity<List<CuriosidadeDTO>> listarTodas() {
        List<Curiosidade> curiosidades = service.listarTodas();
        List<CuriosidadeDTO> dtos = curiosidades.stream().map(c -> {
            CuriosidadeDTO dto = new CuriosidadeDTO();
            dto.setId_curiosidade(c.getIdCuriosidade());
            dto.setDescricao(c.getDescricao());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CuriosidadeDTO> obterPorId(@PathVariable Long id) {
        Optional<Curiosidade> curiosidade = service.obterPorId(id);
        return curiosidade.map(c -> {
                    CuriosidadeDTO dto = new CuriosidadeDTO();
                    dto.setId_curiosidade(c.getIdCuriosidade());
                    dto.setDescricao(c.getDescricao());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<CuriosidadeDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Curiosidade> curiosidades = service.listarPorEquipamento(idEquipamento);
        List<CuriosidadeDTO> dtos = curiosidades.stream().map(c -> {
            CuriosidadeDTO dto = new CuriosidadeDTO();
            dto.setId_curiosidade(c.getIdCuriosidade());
            dto.setDescricao(c.getDescricao());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
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
