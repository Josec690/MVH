package br.org.museu.hardware.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FONTES")
public class Fonte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fonte;

    private String nome_site;
    private String url_original;

    @ManyToOne
    @JoinColumn(name = "FK_id_equipamento")
    private Equipamento equipamento;

    // Getters e Setters
}