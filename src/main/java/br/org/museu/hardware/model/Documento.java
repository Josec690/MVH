package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Equipamento equipamento;

    public Long getIdDoc() {
        return id_doc;
    }

    public void setIdDoc(Long id_doc) {
        this.id_doc = id_doc;
    }
}
