package br.org.museu.hardware.model; // Certifique-se de que o pacote esteja correto e corresponda à estrutura do seu projeto

import jakarta.persistence.*; // Importando as anotações do JPA para definir a entidade e suas propriedades
import lombok.Data; // Importando a anotação do Lombok para gerar getters e setters automaticamente, se você estiver usando o Lombok em seu projeto

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela no banco de dados
@Table(name = "EQUIPAMENTOS") // Especifica o nome da tabela no banco de dados para esta entidade, seguindo a convenção do DER que você forneceu
@Data // Se você instalou o Lombok, isso gera getters e setters automaticamente

public class Equipamento { // Definindo a classe Equipamento, que representa a entidade de equipamentos no sistema
    @Id // Indica que este campo é a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que o valor da chave primária será gerado automaticamente pelo banco de dados, usando a estratégia de identidade
    private Long pk_id_equipo; // Seguindo seu DER // Definindo o campo pk_id_equipo como a chave primária da tabela, do tipo Long
    private String modelo;
    private int ano;
    private String geracao;

    // Depois adicionarei os relacionamentos com as outras entidades, como Fabricante, TipoEquipamento, etc.


}
