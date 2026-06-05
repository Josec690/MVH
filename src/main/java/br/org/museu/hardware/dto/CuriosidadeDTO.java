package br.org.museu.hardware.dto;

public class CuriosidadeDTO {
    private Long id_curiosidade;
    private String descricao;
    private Long idEquipamento;

    public Long getId_curiosidade() {
        return id_curiosidade;
    }

    public void setId_curiosidade(Long id_curiosidade) {
        this.id_curiosidade = id_curiosidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Long idEquipamento) {
        this.idEquipamento = idEquipamento;
    }
}

