package br.jus.tse.prototipo_keikai.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data // Gera Getters, Setters e equals/hashCode via Lombok
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Double valor;
    private String categoria;
}
