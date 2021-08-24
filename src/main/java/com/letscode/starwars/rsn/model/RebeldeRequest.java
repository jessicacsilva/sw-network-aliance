package com.letscode.starwars.rsn.model;

import com.letscode.starwars.rsn.domain.dto.Localizacao;
import com.letscode.starwars.rsn.domain.dto.Rebelde;
import com.letscode.starwars.rsn.domain.dto.Recurso;
import lombok.Data;

import java.util.List;

@Data
public class RebeldeRequest {

    private String nome;
    private Integer idade;
    private String genero;
    private LocalizacaoRequest localizacao;
    private List<RecursoRequest> recurso;

    public  static Rebelde mapRebelde(RebeldeRequest request , Localizacao localizacao){
        Rebelde rebelde = new Rebelde();

        rebelde.setNome(request.getNome());
        rebelde.setGenero(request.getGenero());
        rebelde.setIdade(request.getIdade());
        rebelde.setLocalizacao(localizacao);

        return rebelde;
    }
}
