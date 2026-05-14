package br.org.museu.hardware.controller;

import br.org.museu.hardware.model.Fabricante;
import br.org.museu.hardware.repository.FabricanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/fabricantes")
public class FabrcanteController {
    @Autowired
    private FabricanteRepository repository;

    @GetMapping
    public List<Fabricante> listarTodos() {
        return repository.listarTodos();
    }
}
