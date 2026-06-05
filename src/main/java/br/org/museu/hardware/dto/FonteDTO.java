package br.org.museu.hardware.dto;

public class FonteDTO {
    private Long id_fonte;
    private String nome_site;
    private String url_original;
    private Long idEquipamento;

    public Long getId_fonte() {
        return id_fonte;
    }

    public void setId_fonte(Long id_fonte) {
        this.id_fonte = id_fonte;
    }

    public String getNome_site() {
        return nome_site;
    }

    public void setNome_site(String nome_site) {
        this.nome_site = nome_site;
    }

    public String getUrl_original() {
        return url_original;
    }

    public void setUrl_original(String url_original) {
        this.url_original = url_original;
    }

    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }
}

