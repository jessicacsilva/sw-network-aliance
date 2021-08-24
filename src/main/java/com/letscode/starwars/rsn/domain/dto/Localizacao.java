package com.letscode.starwars.rsn.domain.dto;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Localizacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long latitude;
    private Long longitude;
    private String nome;


    @OneToOne(mappedBy = "localizacao")
    private Rebelde rebelde;
}
