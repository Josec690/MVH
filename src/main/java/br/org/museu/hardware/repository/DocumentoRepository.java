package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Query("select d from Documento d where d.equipamento.id_equipamento = :idEquipamento")
List<Documento> listarPorEquipamento(@org.springframework.data.repository.query.Param("idEquipamento") Long idEquipamento);

    default List<Documento> findByEquipamento_IdEquipamento(Long idEquipamento) {
        return listarPorEquipamento(idEquipamento);
    }
}



