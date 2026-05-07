package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Fonte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonteRepository extends JpaRepository<Fonte, Long> {
}
