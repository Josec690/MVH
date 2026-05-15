package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "IMAGENS")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagem;

    private String formato;
    private String tamanho;
    private String resolucao;

    @ManyToOne
    @JoinColumn(name = "FK_id_equipamento")
    private Equipamento equipamento;

    public Long getIdImagem() {
        return id_imagem;
    }

    public void setIdImagem(Long id_imagem) {
        this.id_imagem = id_imagem;
    }
}
