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
        List<Fonte> fontes = service.listarTodas();
        List<FonteDTO> dtos = fontes.stream().map(f -> {
            FonteDTO dto = new FonteDTO();
            dto.setId_fonte(f.getIdFonte());
            dto.setNome_site(f.getNome_site());
            dto.setUrl_original(f.getUrl_original());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FonteDTO> obterPorId(@PathVariable Long id) {
        Optional<Fonte> fonte = service.obterPorId(id);
        return fonte.map(f -> {
                    FonteDTO dto = new FonteDTO();
                    dto.setId_fonte(f.getIdFonte());
                    dto.setNome_site(f.getNome_site());
                    dto.setUrl_original(f.getUrl_original());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<FonteDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Fonte> fontes = service.listarPorEquipamento(idEquipamento);
        List<FonteDTO> dtos = fontes.stream().map(f -> {
            FonteDTO dto = new FonteDTO();
            dto.setId_fonte(f.getIdFonte());
            dto.setNome_site(f.getNome_site());
            dto.setUrl_original(f.getUrl_original());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
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
