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
        List<CuriosidadeDTO> dtos = service.listarTodas().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuriosidadeDTO> obterPorId(@PathVariable Long id) {
        Optional<Curiosidade> curiosidade = service.obterPorId(id);
        return curiosidade.map(c -> ResponseEntity.ok(toDTO(c)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<CuriosidadeDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<CuriosidadeDTO> dtos = service.listarPorEquipamento(idEquipamento).stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CuriosidadeDTO> criar(@RequestBody CuriosidadeDTO dto) {
        try {
            Curiosidade criada = service.salvar(toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(criada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuriosidadeDTO> atualizar(@PathVariable Long id, @RequestBody CuriosidadeDTO dto) {
        try {
            Curiosidade atualizada = service.atualizar(id, toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.ok(toDTO(atualizada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private CuriosidadeDTO toDTO(Curiosidade curiosidade) {
        CuriosidadeDTO dto = new CuriosidadeDTO();
        dto.setId_curiosidade(curiosidade.getIdCuriosidade());
        dto.setDescricao(curiosidade.getDescricao());
        if (curiosidade.getEquipamento() != null) {
            dto.setIdEquipamento(curiosidade.getEquipamento().getIdEquipamento());
        }
        return dto;
    }

    private Curiosidade toEntity(CuriosidadeDTO dto) {
        Curiosidade curiosidade = new Curiosidade();
        curiosidade.setDescricao(dto.getDescricao());
        return curiosidade;
    }
}
