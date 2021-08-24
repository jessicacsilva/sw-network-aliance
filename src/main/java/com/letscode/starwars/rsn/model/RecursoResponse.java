package com.letscode.starwars.rsn.model;

public class RecursoResponse {
    public RecursoResponse(EnumRecursos tipoRecurso, Long quantidade) {
        this.tipoRecurso = tipoRecurso;
        this.quantidade = quantidade;
    }

    private EnumRecursos tipoRecurso;
    private Long quantidade;


}
