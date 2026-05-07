package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Emulador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmuladorRepository extends JpaRepository<Emulador, Long> {
}
