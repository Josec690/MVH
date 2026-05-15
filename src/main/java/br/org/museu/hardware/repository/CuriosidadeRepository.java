package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Curiosidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuriosidadeRepository extends JpaRepository<Curiosidade, Long> {
    List<Curiosidade> findByEquipamento_IdEquipamento(Long idEquipamento);
}

