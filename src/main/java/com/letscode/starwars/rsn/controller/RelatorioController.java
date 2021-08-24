package com.letscode.starwars.rsn.controller;

import com.letscode.starwars.rsn.model.RecursoResponse;
import com.letscode.starwars.rsn.service.RebeldeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/relatorio")
public class RelatorioController {

    private final RebeldeService rebeldeService;

    public RelatorioController(RebeldeService rebeldeService) {
        this.rebeldeService = rebeldeService;
    }

    @GetMapping("/traidores")
    @Operation(description = "Retornar percentual traidores")
    public Double getTraiores(){
        return rebeldeService.getTraidores();
    }

    @GetMapping("/rebeldes")
    @Operation(description = "Retornar percentual rebeldes")
    public Double getRebeldes(){
        return rebeldeService.getRebeldes();
    }

    @GetMapping("/recursos/media")
    @Operation(description = "Retornar média recursos")
    public List<RecursoResponse> getMediaRecursos(){
        return rebeldeService.getMediaRecursos();
    }

    @GetMapping("/pontosPerdidos")
    @Operation(description = "Retornar pontos perdidos")
    public Long getPontosPerdidos(){
        return rebeldeService.getPontosPerdidos();
    }
}
