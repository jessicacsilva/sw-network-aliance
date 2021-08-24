package com.letscode.starwars.rsn.service;

import com.letscode.starwars.rsn.domain.dto.Rebelde;
import com.letscode.starwars.rsn.domain.dto.Recurso;
import com.letscode.starwars.rsn.model.EnumRecursos;
import com.letscode.starwars.rsn.model.RecursoRequest;
import com.letscode.starwars.rsn.model.RecursoResponse;
import com.letscode.starwars.rsn.repository.LocalizacaoRepository;
import com.letscode.starwars.rsn.repository.RebeldeRepository;
import com.letscode.starwars.rsn.repository.RecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RebeldeServiceTest {

    @Autowired
    private RebeldeService rebeldeService;
    @MockBean
    private  RebeldeRepository rebeldeRepositoryMock;
    @MockBean
    private  LocalizacaoRepository localizacaoRepositoryMock;
    @MockBean
    private  RecursoRepository recursoRepositoryMock;


    private Rebelde rebeldeMock;
    private Rebelde rebeldeMock2;
    private Rebelde rebeldeMock3;

    @BeforeEach
    void buildMocks() {
        Recurso recurso =  Recurso.builder().build();
        recurso.setTipoRecurso(EnumRecursos.ARMA);
        recurso.setQuantidade(2);
        List<Recurso> recursos = new ArrayList<>();
        recursos.add(recurso);

        rebeldeMock = new Rebelde();
        rebeldeMock2 = new Rebelde();
        rebeldeMock3 = new Rebelde();

        rebeldeMock.setId(1l);
        rebeldeMock.setNome("Anaquin");
        rebeldeMock.setIdade(50);
        rebeldeMock.setETraidor(true);
        rebeldeMock.setGenero("M");
        rebeldeMock.setContadorNotificacao(3);
        rebeldeMock.setRecurso(recursos);

        rebeldeMock2.setId(3l);
        rebeldeMock2.setNome("Luke");
        rebeldeMock2.setIdade(30);
        rebeldeMock2.setGenero("M");
        rebeldeMock2.setRecurso(recursos);


        rebeldeMock3.setId(4l);
        rebeldeMock3.setNome("Rey");
        rebeldeMock3.setIdade(20);
        rebeldeMock3.setGenero("F");
        rebeldeMock3.setRecurso(recursos);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void cadastrarRebelde() {
    }

    @DisplayName("Barra negociação com traidor")
    @Test
    public void deveRetornarErroAoNegociarRecursosComTraidor() {
        Long idRebelde  = 1l;
        Long idRebelde2 =3l;
        List<RecursoRequest> recursosRebelde1 = new ArrayList<>();
        RecursoRequest recursoRequest = new RecursoRequest();
        recursoRequest.setTipoRecurso(EnumRecursos.ARMA);
        recursoRequest.setQuantidade(1);
        recursosRebelde1.add(recursoRequest);

        List<RecursoRequest> recursosRebelde2 = new ArrayList<>();
        recursosRebelde2.add(recursoRequest);

        ResponseEntity returnMock = ResponseEntity.status(HttpStatus.FORBIDDEN).body("Foi detectado um traidor!");

        when(rebeldeRepositoryMock.getById(1l)).thenReturn(rebeldeMock);
        when(rebeldeRepositoryMock.getById(3l)).thenReturn(rebeldeMock2);

        ResponseEntity responseEntity =  rebeldeService.negociarRecursos(idRebelde,idRebelde2,recursosRebelde1,recursosRebelde2);

        assertEquals(returnMock,responseEntity);
    }


    @DisplayName("Negociação entre Rebeldes com sucesso")
    @Test
    public void deveRetornarErroAoNegociarRecursos() {
        Long idRebelde  = 4l;
        Long idRebelde2 =3l;
        List<RecursoRequest> recursosRebelde1 = new ArrayList<>();
        RecursoRequest recursoRequest = new RecursoRequest();
        recursoRequest.setTipoRecurso(EnumRecursos.ARMA);
        recursoRequest.setQuantidade(1);
        recursosRebelde1.add(recursoRequest);

        List<RecursoRequest> recursosRebelde2 = new ArrayList<>();
        recursosRebelde2.add(recursoRequest);

        ResponseEntity returnMock = ResponseEntity.status(HttpStatus.OK).body("");

        when(rebeldeRepositoryMock.getById(4l)).thenReturn(rebeldeMock3);
        when(rebeldeRepositoryMock.getById(3l)).thenReturn(rebeldeMock2);

        ResponseEntity responseEntity =  rebeldeService.negociarRecursos(idRebelde,idRebelde2,recursosRebelde1,recursosRebelde2);

        assertEquals(returnMock,responseEntity);
    }

    @DisplayName("Valida percentual de traidores")
    @Test
    void deveRetornarOPercentualCorretoDeTraidores() {
        List<Rebelde> rebeldesMock= new ArrayList<>();
        rebeldesMock.add(rebeldeMock);
        rebeldesMock.add(rebeldeMock2);

        when(rebeldeRepositoryMock.findAll()).thenReturn(rebeldesMock);

        Double responseEntity =  rebeldeService.getTraidores();

        assertEquals(50,responseEntity);
    }

    @DisplayName("Valida percentual de rebeldes")
    @Test
    void deveRetornarOPercentualCorretoDeRebeldes() {
        List<Rebelde> rebeldesMock= new ArrayList<>();
        rebeldesMock.add(rebeldeMock);
        rebeldesMock.add(rebeldeMock2);
        rebeldesMock.add(rebeldeMock3);

        when(rebeldeRepositoryMock.findAll()).thenReturn(rebeldesMock);

        Double responseEntity =  rebeldeService.getRebeldes();

        assertEquals(67,responseEntity);
    }

    @Test
    @DisplayName("Valida media  de recursos")
    void deveRetornarAMediaCorretaEDesconsiderarOTraidor() {
        List<Rebelde> rebeldesMock= new ArrayList<>();
        rebeldesMock.add(rebeldeMock);
        rebeldesMock.add(rebeldeMock2);
        rebeldesMock.add(rebeldeMock3);

        when(rebeldeRepositoryMock.findAll()).thenReturn(rebeldesMock);

        List<RecursoResponse>  responseEntity =  rebeldeService.getMediaRecursos();

        assertEquals(EnumRecursos.ARMA,responseEntity.get(0).getTipoRecurso());
        assertEquals(1,responseEntity.get(0).getQuantidade());
    }

    @Test
    @DisplayName("Valida pontos perdidos com traidores")
    void deveRetornarOValorCorretoDePontosPerdidos() {
        List<Rebelde> rebeldesMock= new ArrayList<>();
        rebeldesMock.add(rebeldeMock);
        rebeldesMock.add(rebeldeMock2);
        rebeldesMock.add(rebeldeMock3);

        when(rebeldeRepositoryMock.findAll()).thenReturn(rebeldesMock);

        Long pontosPerdidos =  rebeldeService.getPontosPerdidos();

        assertEquals(8,pontosPerdidos);
    }
}