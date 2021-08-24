package com.letscode.starwars.rsn.domain.dto;

import com.letscode.starwars.rsn.model.EnumRecursos;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Builder
@Data
@Entity
public class Recurso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private EnumRecursos tipoRecurso;
    private Integer quantidade;

    @ManyToOne
    private Rebelde rebelde;
}
