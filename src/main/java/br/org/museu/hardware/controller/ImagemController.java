package br.org.museu.hardware.controller;

import br.org.museu.hardware.dto.ImagemDTO;
import br.org.museu.hardware.model.Imagem;
import br.org.museu.hardware.service.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/imagens")
@CrossOrigin(origins = "*")
public class ImagemController {

    @Autowired
    private ImagemService service;

    @GetMapping
    public ResponseEntity<List<ImagemDTO>> listarTodas() {
        List<ImagemDTO> dtos = service.listarTodas().stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagemDTO> obterPorId(@PathVariable Long id) {
        Optional<Imagem> imagem = service.obterPorId(id);
        return imagem.map(i -> ResponseEntity.ok(toDTO(i)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<ImagemDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<ImagemDTO> dtos = service.listarPorEquipamento(idEquipamento).stream()
                .map(this::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ImagemDTO> criar(@RequestBody ImagemDTO dto) {
        try {
            Imagem criada = service.salvar(toEntity(dto), dto.getIdEquipamento());
            return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(criada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagemDTO> atualizar(@PathVariable Long id, @RequestBody ImagemDTO dto) {
        try {
            Imagem atualizada = service.atualizar(id, toEntity(dto), dto.getIdEquipamento());
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

    private ImagemDTO toDTO(Imagem imagem) {
        ImagemDTO dto = new ImagemDTO();
        dto.setId_imagem(imagem.getIdImagem());
        dto.setFormato(imagem.getFormato());
        dto.setTamanho(imagem.getTamanho());
        dto.setResolucao(imagem.getResolucao());
        dto.setUrl(imagem.getUrl());
        if (imagem.getEquipamento() != null) {
            dto.setIdEquipamento(imagem.getEquipamento().getIdEquipamento());
        }
        return dto;
    }

    private Imagem toEntity(ImagemDTO dto) {
        Imagem imagem = new Imagem();
        imagem.setFormato(dto.getFormato());
        imagem.setTamanho(dto.getTamanho());
        imagem.setResolucao(dto.getResolucao());
        imagem.setUrl(dto.getUrl());
        return imagem;
    }
}
