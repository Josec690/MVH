package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Curiosidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface CuriosidadeRepository extends JpaRepository<Curiosidade, Long> {

    @Query("select c from Curiosidade c where c.equipamento.id_equipamento = :idEquipamento")
List<Curiosidade> listarPorEquipamento(@org.springframework.data.repository.query.Param("idEquipamento") Long idEquipamento);

    default List<Curiosidade> findByEquipamento_IdEquipamento(Long idEquipamento) {
        return listarPorEquipamento(idEquipamento);
    }
}



