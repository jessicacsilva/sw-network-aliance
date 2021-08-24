package com.letscode.starwars.rsn.domain.dto;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Rebelde {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer idade;
    private String genero;
    private Boolean eTraidor;
    private Integer contadorNotificacao;

    @Transient
    @OneToOne
    private Localizacao localizacao;

    @OneToMany(mappedBy = "rebelde")
    private List<Recurso> recurso;
}
