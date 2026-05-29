package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FABRICANTES")
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_fabricante;

    private String nome;
    private String pais;

@OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Equipamento> equipamentos;

    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emulador> emuladores;

    public Long getIdFabricante() {
        return id_fabricante;
    }

    public void setIdFabricante(Long id_fabricante) {
        this.id_fabricante = id_fabricante;
    }
}
