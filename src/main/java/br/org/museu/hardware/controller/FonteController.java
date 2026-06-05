package br.org.museu.hardware.controller;

import br.org.museu.hardware.dto.FonteDTO;
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
    public ResponseEntity<List<FonteDTO>> listarTodas() {
        List<FonteDTO> dtos = service.listarTodas().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FonteDTO> obterPorId(@PathVariable Long id) {
        Optional<Fonte> fonte = service.obterPorId(id);
        return fonte.map(f -> ResponseEntity.ok(toDTO(f)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<FonteDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<FonteDTO> dtos = service.listarPorEquipamento(idEquipamento).stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<FonteDTO> criar(@RequestBody FonteDTO dto) {
        try {
            Fonte criada = service.salvar(toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(criada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FonteDTO> atualizar(@PathVariable Long id, @RequestBody FonteDTO dto) {
        try {
            Fonte atualizada = service.atualizar(id, toEntity(dto), dto.getIdEquipamento());
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

    private FonteDTO toDTO(Fonte fonte) {
        FonteDTO dto = new FonteDTO();
        dto.setId_fonte(fonte.getIdFonte());
        dto.setNome_site(fonte.getNome_site());
        dto.setUrl_original(fonte.getUrl_original());
        if (fonte.getEquipamento() != null) {
            dto.setIdEquipamento(fonte.getEquipamento().getIdEquipamento());
        }
        return dto;
    }

    private Fonte toEntity(FonteDTO dto) {
        Fonte fonte = new Fonte();
        fonte.setNome_site(dto.getNome_site());
        fonte.setUrl_original(dto.getUrl_original());
        return fonte;
    }
}
