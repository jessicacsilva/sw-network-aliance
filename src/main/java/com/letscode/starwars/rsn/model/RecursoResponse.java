package com.letscode.starwars.rsn.model;

import lombok.Data;

@Data
public class RecursoResponse {
    public RecursoResponse(EnumRecursos tipoRecurso, Long quantidade) {
        this.tipoRecurso = tipoRecurso;
        this.quantidade = quantidade;
    }

    private EnumRecursos tipoRecurso;
    private Long quantidade;


}
