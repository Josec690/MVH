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
        List<Imagem> imagens = service.listarTodas();
        List<ImagemDTO> dtos = imagens.stream().map(i -> {
            ImagemDTO dto = new ImagemDTO();
            dto.setId_imagem(i.getIdImagem());
            dto.setFormato(i.getFormato());
            dto.setTamanho(i.getTamanho());
            dto.setResolucao(i.getResolucao());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ImagemDTO> obterPorId(@PathVariable Long id) {
        Optional<Imagem> imagem = service.obterPorId(id);
        return imagem.map(i -> {
                    ImagemDTO dto = new ImagemDTO();
                    dto.setId_imagem(i.getIdImagem());
                    dto.setFormato(i.getFormato());
                    dto.setTamanho(i.getTamanho());
                    dto.setResolucao(i.getResolucao());
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<ImagemDTO>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Imagem> imagens = service.listarPorEquipamento(idEquipamento);
        List<ImagemDTO> dtos = imagens.stream().map(i -> {
            ImagemDTO dto = new ImagemDTO();
            dto.setId_imagem(i.getIdImagem());
            dto.setFormato(i.getFormato());
            dto.setTamanho(i.getTamanho());
            dto.setResolucao(i.getResolucao());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }


    @PostMapping
    public ResponseEntity<Imagem> criar(@RequestBody Imagem imagem) {
        try {
            Imagem criada = service.salvar(imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Imagem> atualizar(@PathVariable Long id, @RequestBody Imagem imagem) {
        try {
            Imagem atualizada = service.atualizar(id, imagem);
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
