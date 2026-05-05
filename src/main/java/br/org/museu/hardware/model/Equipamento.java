package br.org.museu.hardware.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Equipamentos")
@Data // Se você instalou o Lombok, isso gera getters e setters automaticamente

public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk_id_equipo; //

}
