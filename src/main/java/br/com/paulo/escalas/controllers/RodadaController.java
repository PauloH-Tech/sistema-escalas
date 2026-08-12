package br.com.paulo.escalas.controllers;

import br.com.paulo.escalas.entities.militares.MilitarDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import br.com.paulo.escalas.services.RodadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<List<RodadaEscala>> listar(){
        List<RodadaEscala> escalas = service.listarTodas();
        return ResponseEntity.ok(escalas);
    }

    @GetMapping("/proximas")
    public ResponseEntity<List<RodadaEscala>> proximasRodadas(){
        List<RodadaEscala> nextEscalas = service.listarProximasRodadas();
        return ResponseEntity.ok(nextEscalas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarRodada(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
