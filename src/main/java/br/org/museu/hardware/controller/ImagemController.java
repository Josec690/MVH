package br.org.museu.hardware.controller;

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
    public ResponseEntity<List<Imagem>> listarTodas() {
        List<Imagem> imagens = service.listarTodas();
        return ResponseEntity.ok(imagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagem> obterPorId(@PathVariable Long id) {
        Optional<Imagem> imagem = service.obterPorId(id);
        return imagem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/equipamento/{idEquipamento}")
    public ResponseEntity<List<Imagem>> listarPorEquipamento(@PathVariable Long idEquipamento) {
        List<Imagem> imagens = service.listarPorEquipamento(idEquipamento);
        return ResponseEntity.ok(imagens);
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
