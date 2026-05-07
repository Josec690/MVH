package br.org.museu.hardware.repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    // Aqui o Spring já te dá save(), findAll(), findById(), etc.
}