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
    private Fabricante fabricante;
}
