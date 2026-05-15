package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Emulador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmuladorRepository extends JpaRepository<Emulador, Long> {
    List<Emulador> findByFabricante_IdFabricante(Long idFabricante);
}

