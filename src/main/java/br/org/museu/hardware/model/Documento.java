package br.org.museu.hardware.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DOCUMENTOS")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_doc;

    private String titulo;
    private String url;

    @ManyToOne
    @JoinColumn(name = "FK_id_equipamento")
    private Equipamento equipamento;

    // Getters e Setters
}