package com.letscode.starwars.rsn.model;

import com.letscode.starwars.rsn.domain.dto.Localizacao;
import lombok.Data;

@Data
public class LocalizacaoRequest {
    private Long latitude;
    private Long longitude;
    private String nome;

    public static Localizacao mapLocalizacao(LocalizacaoRequest request){
        Localizacao localizacao = new Localizacao();

        localizacao.setLongitude(request.getLongitude());
        localizacao.setLatitude(request.getLatitude());
        localizacao.setNome(request.getNome());

        return localizacao;
    }
}
