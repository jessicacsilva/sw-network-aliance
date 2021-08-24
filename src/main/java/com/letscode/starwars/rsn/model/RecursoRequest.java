package com.letscode.starwars.rsn.model;

import com.letscode.starwars.rsn.domain.dto.Rebelde;
import com.letscode.starwars.rsn.domain.dto.Recurso;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RecursoRequest {
    private EnumRecursos tipoRecurso;
    private Integer quantidade;

    public static Recurso mapRecurso(RecursoRequest request, Rebelde rebelde){
        return Recurso.builder()
                .tipoRecurso(request.getTipoRecurso())
                .quantidade(request.getQuantidade())
                .rebelde(rebelde).build();
    }
}
