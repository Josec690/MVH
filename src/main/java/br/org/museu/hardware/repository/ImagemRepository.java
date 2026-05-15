package br.org.museu.hardware.repository;

import br.org.museu.hardware.model.Imagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    List<Imagem> findByEquipamento_IdEquipamento(Long idEquipamento);
}

