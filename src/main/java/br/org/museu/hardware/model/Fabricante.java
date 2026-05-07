package br.org.museu.hardware.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "FABRICANTES")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fabricante;

    private String nome;
    private String pais;

    // Relacionamento 1..N com EQUIPAMENTOS
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    private List<Equipamento> EQUIPAMENTOS;

    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL)
    private List<Emulador> EMULADORES;

    // Getters e Setters
}