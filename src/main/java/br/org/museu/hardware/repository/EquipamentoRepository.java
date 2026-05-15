package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    List<Equipamento> findByFabricante_IdFabricante(Long idFabricante);
    List<Equipamento> findByGeracao(String geracao);
}

