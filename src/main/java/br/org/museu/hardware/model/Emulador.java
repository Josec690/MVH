package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "EMULADORES")
public class Emulador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_emulador;

    private String nome_emu;
    private String versao;

    @ManyToOne
    @JoinColumn(name = "FK_id_fabricante")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Fabricante fabricante;

    public Long getIdEmulador() {
        return id_emulador;
    }

    public void setIdEmulador(Long id_emulador) {
        this.id_emulador = id_emulador;
    }

    public String getNomeEmu() {
        return nome_emu;
    }

    public void setNomeEmu(String nome_emu) {
        this.nome_emu = nome_emu;
    }
}
