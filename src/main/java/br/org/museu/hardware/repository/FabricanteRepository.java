package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Fabricante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FabricanteRepository extends JpaRepository<Fabricante, Long> {
    List<Fabricante> findByPaisContainingIgnoreCase(String pais);
}

