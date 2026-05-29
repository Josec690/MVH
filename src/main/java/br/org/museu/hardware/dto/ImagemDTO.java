package br.org.museu.hardware.dto;

public class ImagemDTO {
    private Long id_imagem;
    private String formato;
    private String tamanho;
    private String resolucao;

    public Long getId_imagem() {
        return id_imagem;
    }

    public void setId_imagem(Long id_imagem) {
        this.id_imagem = id_imagem;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }
}

