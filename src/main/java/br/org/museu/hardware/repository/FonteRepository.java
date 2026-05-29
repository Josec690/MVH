package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Fonte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface FonteRepository extends JpaRepository<Fonte, Long> {

    @Query("select f from Fonte f where f.equipamento.id_equipamento = :idEquipamento")
List<Fonte> listarPorEquipamento(@org.springframework.data.repository.query.Param("idEquipamento") Long idEquipamento);

    // compat: manter assinatura antiga para não quebrar services/controller (caso ainda usem o método derivado)
    default List<Fonte> findByEquipamento_IdEquipamento(Long idEquipamento) {
        return listarPorEquipamento(idEquipamento);
    }

}


