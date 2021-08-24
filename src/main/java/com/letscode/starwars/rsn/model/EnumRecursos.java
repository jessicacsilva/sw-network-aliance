package com.letscode.starwars.rsn.model;

public enum EnumRecursos {
    ARMA("Arma",4),
    MUNICAO("Munição",3),
    AGUA("Água",2),
    COMIDA("Comida",1);

    EnumRecursos(String nome, Integer pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    private String nome;
    private Integer pontos;

    public static Integer getPontos(String nome){
        for(EnumRecursos e : values()) {
            if(e.nome.equals(nome)) return e.pontos;
        }
        return null;
    }
}
