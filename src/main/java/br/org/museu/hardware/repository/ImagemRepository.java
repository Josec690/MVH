package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {

    @Query("select i from Imagem i where i.equipamento.id_equipamento = :idEquipamento")
List<Imagem> listarPorEquipamento(@org.springframework.data.repository.query.Param("idEquipamento") Long idEquipamento);

    default List<Imagem> findByEquipamento_IdEquipamento(Long idEquipamento) {
        return listarPorEquipamento(idEquipamento);
    }
}



