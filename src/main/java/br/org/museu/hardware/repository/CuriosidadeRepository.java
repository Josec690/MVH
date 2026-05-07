package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Curiosidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuriosidadeRepository extends JpaRepository<Curiosidade, Long> {
    // Você pode adicionar métodos customizados depois, como:
    // List<Curiosidade> findByEquipamentoId(Long id);
}
