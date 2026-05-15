package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fonte> fontes;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documento> documentos;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Imagem> imagens;

    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Curiosidade> curiosidades;

    public Long getIdEquipamento() {
        return id_equipamento;
    }

    public void setIdEquipamento(Long id_equipamento) {
        this.id_equipamento = id_equipamento;
    }
}