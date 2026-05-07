package br.org.museu.hardware.controller;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository repository;

    @GetMapping
    public List<Equipamento> listarTodos() {
        return repository.findAll();
    }

    @PostMapping
    public Equipamento salvar(@RequestBody Equipamento equipamento) {
        return repository.save(equipamento);
    }
}
