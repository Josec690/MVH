package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Equipamento;
import br.org.museu.hardware.repository.EquipamentoRepository;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository repository;

    @GetMapping
    public List<Equipamento> listarTodos() {
        return repository.listarTodos();
    }

    @PostMapping
    public Equipamento salvar(@RequestBody Equipamento equipamento) {
        return repository.save(equipamento);
    }
}
