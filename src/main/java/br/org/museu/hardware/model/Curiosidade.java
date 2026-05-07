package br.org.museu.hardware.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CURIOSIDADES")
public class Curiosidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_curiosidade;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "FK_id_equipamento")
    private Equipamento equipamento;

    // Getters e Setters
}