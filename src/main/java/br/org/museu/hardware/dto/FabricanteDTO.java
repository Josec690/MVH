package br.org.museu.hardware.dto;

public class FabricanteDTO {
    private Long id_fabricante;
    private String nome;
    private String pais;

    public Long getId_fabricante() {
        return id_fabricante;
    }

    public void setId_fabricante(Long id_fabricante) {
        this.id_fabricante = id_fabricante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}

