package com.letscode.starwars.rsn.controller;

import com.letscode.starwars.rsn.model.LocalizacaoRequest;
import com.letscode.starwars.rsn.model.RebeldeRequest;
import com.letscode.starwars.rsn.model.RecursoRequest;
import com.letscode.starwars.rsn.service.RebeldeService;
import io.swagger.v3.oas.annotations.Operation;

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
    public ResponseEntity criar(@RequestBody RebeldeRequest request){
        return  rebeldeService.cadastrarRebelde(request);
    }

    @PutMapping("/rebelde/{id}")
    @Operation(description = "Atualizar a localização de um rebelde")
    public ResponseEntity atualizarLocalizacao(@RequestBody LocalizacaoRequest request,@PathVariable Long id) {
        return rebeldeService.atualizarLocalizacao(request,id);
    }

    @PatchMapping("/traidor/{id}")
    @Operation(description = "Reportar rebelde como traidor")
    public ResponseEntity reportarTraidor(@PathVariable Long id) {
        return rebeldeService.reportarTraidor(id);
    }

    @PutMapping("/troca/rebelde/{idRebelde}/rebelde2/{idRebelde2}")
    @Operation(description = "Negociar recursos")
    public ResponseEntity  negociarRecursos(@PathVariable Long idRebelde,
                                           @PathVariable Long idRebelde2,
                                           @RequestBody List<List<RecursoRequest>> recursosRebelde) {
       return rebeldeService.negociarRecursos(idRebelde,idRebelde2,recursosRebelde);
    }
}
