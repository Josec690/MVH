package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EQUIPAMENTOS")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_equipamento;

    private String modelo;
    private int ano;
    private String geracao;

    @ManyToOne
    @JoinColumn(name = "FK_id_fabricante")
    private Fabricante fabricante;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL)
    private List<Fonte> FONTES;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL)
    private List<Documento> ;

    // Outros relacionamentos como IMAGENS e  seguirão o mesmo padrão OneToMany

    // Getters e Setters
}