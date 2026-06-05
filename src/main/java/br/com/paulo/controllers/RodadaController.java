package br.com.paulo.controllers;

import br.com.paulo.entities.rodadas.RodadaDTO;
import br.com.paulo.entities.rodadas.RodadaEscala;
import br.com.paulo.services.RodadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rodada")
public class RodadaController {

    @Autowired
    private RodadaService service;


    @PostMapping
    public ResponseEntity<?> cadastraRodada(@RequestBody RodadaDTO dto){
        service.cadastrarRodada(dto);
        //TODO: Retornar o id da escala
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<RodadaEscala>> proximasRodadas(){
        List<RodadaEscala> nextEscalas = service.listarProximasRodadas();

        return ResponseEntity.ok(nextEscalas);
    }


}
