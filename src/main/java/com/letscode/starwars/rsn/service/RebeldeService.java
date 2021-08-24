package com.letscode.starwars.rsn.service;

import com.letscode.starwars.rsn.domain.dto.Localizacao;
import com.letscode.starwars.rsn.domain.dto.Rebelde;
import com.letscode.starwars.rsn.model.*;
import com.letscode.starwars.rsn.repository.LocalizacaoRepository;
import com.letscode.starwars.rsn.repository.RebeldeRepository;
import com.letscode.starwars.rsn.repository.RecursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RebeldeService {

    private final RebeldeRepository rebeldeRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final RecursoRepository recursoRepository;

    private Long ARMA = 0l;
    private Long MUNICAO = 0l;
    private Long AGUA = 0l;
    private Long COMIDA = 0l;


    public RebeldeService(RebeldeRepository rebeldeRepository, LocalizacaoRepository localizacaoRepository, RecursoRepository recursoRepository) {
        this.rebeldeRepository = rebeldeRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.recursoRepository = recursoRepository;
    }

    public void cadastrarRebelde(RebeldeRequest request){

        Rebelde rebelde = rebeldeRepository.save(
                RebeldeRequest.mapRebelde(request,
                        localizacaoRepository.save(LocalizacaoRequest
                                .mapLocalizacao(request.getLocalizacao()))));

        atualizarRecursos(rebelde.getId(),request.getRecurso());

    }

    public void atualizarLocalizacao(LocalizacaoRequest localizacaoRequest, Long id){
        Rebelde rebelde = rebeldeRepository.getById(id);

        Localizacao localizacao = localizacaoRepository.getById(rebelde.getLocalizacao().getId());
        localizacao.setNome(localizacaoRequest.getNome());
        localizacao.setLongitude(localizacaoRequest.getLongitude());
        localizacao.setLatitude(localizacaoRequest.getLatitude());

        localizacaoRepository.save(localizacao);
    }

    public  void reportarTraidor(Long id){
        Rebelde rebelde = rebeldeRepository.getById(id);
        Integer contador = rebelde.getContadorNotificacao();
        if( contador >=  3){
            rebelde.setETraidor(true);
        }

        rebelde.setContadorNotificacao(contador++);

        rebeldeRepository.save(rebelde);
    }

    public ResponseEntity negociarRecursos(Long idRebelde1,
                                           Long idRebelde2,
                                           List<RecursoRequest> recursosRebelde1,
                                           List<RecursoRequest> recursosRebelde2) {

        if ( verificaTraidor(idRebelde1) || verificaTraidor(idRebelde2)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Foi detectado um traidor!");
        }
        Integer somaRecursosRebelde1 = recursosRebelde1.stream().map(r1 -> r1.getQuantidade() * (EnumRecursos.getPontos(r1.getTipoRecurso().name()))).mapToInt(Integer::intValue).sum();
        Integer somaRecursosRebelde2 = recursosRebelde2.stream().map(r1 -> r1.getQuantidade() * (EnumRecursos.getPontos(r1.getTipoRecurso().name()))).mapToInt(Integer::intValue).sum();

        if(somaRecursosRebelde1==somaRecursosRebelde2){
            atualizarRecursos(idRebelde1,recursosRebelde2);
            atualizarRecursos(idRebelde2,recursosRebelde1);
        }

        return ResponseEntity.status(HttpStatus.OK).body("");
    }


    public Double getTraidores(){
        List<Rebelde> rebelde = rebeldeRepository.findAll();

        long total = rebelde.stream().count();

        Optional<Rebelde> totalTraiores = rebelde.stream().filter(t->t.getETraidor().equals(true)).findAny();

        return  Double.valueOf(totalTraiores.stream().count() * 100/total);

    }

    public Double getRebeldes(){
        Double totalRebeldes = 100 -  getTraidores();

        return totalRebeldes;
    }

    public List<RecursoResponse> getMediaRecursos(){
        List<Rebelde> rebeldes = rebeldeRepository.findAll();
        List<RecursoResponse> recursoResponses = new ArrayList<>();
        Long contador = 0l;

        for (Rebelde rebelde:rebeldes) {
            if(!rebelde.getETraidor()){
                ARMA += rebelde.getRecurso().stream().filter(r->r.getTipoRecurso().equals(EnumRecursos.ARMA)).count();
                MUNICAO += rebelde.getRecurso().stream().filter(r->r.getTipoRecurso().equals(EnumRecursos.MUNICAO)).count();
                AGUA += rebelde.getRecurso().stream().filter(r->r.getTipoRecurso().equals(EnumRecursos.AGUA)).count();
                COMIDA += rebelde.getRecurso().stream().filter(r->r.getTipoRecurso().equals(EnumRecursos.COMIDA)).count();
                contador++;
            }
        }
        recursoResponses.add( new RecursoResponse(EnumRecursos.ARMA, ARMA/contador));
        recursoResponses.add( new RecursoResponse(EnumRecursos.MUNICAO, MUNICAO/contador));
        recursoResponses.add( new RecursoResponse(EnumRecursos.AGUA, AGUA/contador));
        recursoResponses.add( new RecursoResponse(EnumRecursos.COMIDA, COMIDA/contador));


        return recursoResponses;
    }

    public Long getPontosPerdidos(){
        List<Rebelde> rebelde = rebeldeRepository.findAll();
        Long pontosPerdidos = 0l;

        long total = rebelde.stream().count();

        List<Rebelde> traidores = rebelde.stream().filter(t->t.getETraidor().equals(true)).collect(Collectors.toList());

        for (Rebelde traidor:traidores) {
            pontosPerdidos +=   traidor.getRecurso().stream().map(r-> r.getQuantidade() * (EnumRecursos.getPontos(r.getTipoRecurso().name()))).mapToLong(Integer::intValue).sum();
        }

        return pontosPerdidos;
    }

    private void atualizarRecursos(Long id,  List<RecursoRequest> requests){
        Rebelde rebelde = rebeldeRepository.getById(id);

        for (RecursoRequest item: requests) {
            recursoRepository.save(RecursoRequest.mapRecurso(item,rebelde));
        }
    }

    private boolean verificaTraidor(Long id){
        Rebelde rebelde = rebeldeRepository.getById(id);

        return rebelde.getETraidor();
    }
}
