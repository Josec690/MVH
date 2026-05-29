package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Equipamento equipamento;

    public Long getIdFonte() {
        return id_fonte;
    }

    public void setIdFonte(Long id_fonte) {
        this.id_fonte = id_fonte;
    }

    public String getNomeSite() {
        return nome_site;
    }

    public void setNomeSite(String nome_site) {
        this.nome_site = nome_site;
    }

    public String getUrlOriginal() {
        return url_original;
    }

    public void setUrlOriginal(String url_original) {
        this.url_original = url_original;
    }
}
