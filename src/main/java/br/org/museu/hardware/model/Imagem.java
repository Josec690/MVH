package br.org.museu.hardware.model;
@Entity
@Table(name = "IMAGENS")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_imagem;

    private String formato;   // Ex: .png, .jpg
    private String tamanho;   // Ex: 2MB
    private String resolucao; // Ex: 1920x1080

    @ManyToOne
    @JoinColumn(name = "FK_id_equipamento")
    private Equipamento equipamento;

    // Getters e Setters
}