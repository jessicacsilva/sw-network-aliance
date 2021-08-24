package com.letscode.starwars.rsn.model;

public enum EnumRecursos {
    ARMA("ARMA",4),
    MUNICAO("MUNICAO",3),
    AGUA("AGUA",2),
    COMIDA("COMIDA",1);

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
