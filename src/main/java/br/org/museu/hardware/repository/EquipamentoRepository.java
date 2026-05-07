package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    // Aqui o Spring já te dá save(), findAll(), findById(), etc.
}

