package com.letscode.starwars.rsn.controller;

import com.letscode.starwars.rsn.model.LocalizacaoRequest;
import com.letscode.starwars.rsn.model.RebeldeRequest;
import com.letscode.starwars.rsn.model.RecursoRequest;
import com.letscode.starwars.rsn.model.RecursoResponse;
import com.letscode.starwars.rsn.service.RebeldeService;
import io.swagger.v3.oas.annotations.Operation;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/rebelde")
public class RebeldeController {
    private final RebeldeService rebeldeService;

    public RebeldeController(RebeldeService rebeldeService) {
        this.rebeldeService = rebeldeService;
    }

    @PostMapping
    @Operation(description = "Cadastrar um novo rebelde")
    public void criar(@RequestBody RebeldeRequest request){
        rebeldeService.cadastrarRebelde(request);
    }

    @PutMapping("/rebelde/{id}")
    @Operation(description = "Atualizar a localização de um rebelde")
    public void atualizarLocalizacao(@RequestBody LocalizacaoRequest request,@PathVariable Long id) {
        rebeldeService.atualizarLocalizacao(request,id);
    }

    @PutMapping("/traidor/{id}")
    @Operation(description = "Reportar rebelde como traidor")
    public void reportarTraidor(@PathVariable Long id) {
        rebeldeService.reportarTraidor(id);
    }

    @PutMapping("/troca/rebelde/{idRebelde}/rebelde2/{idRebelde2}")
    @Operation(description = "Negociar recursos")
    public ResponseEntity  negociarRecursos(@PathVariable Long idRebelde,
                                           @PathVariable Long idRebelde2,
                                           @RequestBody List<RecursoRequest> recursosRebelde1,
                                           @RequestBody List<RecursoRequest> recursosRebelde2) {
       return rebeldeService.negociarRecursos(idRebelde,idRebelde2,recursosRebelde1,recursosRebelde2);
    }
}
