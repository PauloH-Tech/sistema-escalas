package br.com.paulo.controllers;

import br.com.paulo.entities.rodadas.RodadaDTO;
import br.com.paulo.services.RodadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rodada")
public class RodadaController {

    @Autowired
    private RodadaService service;


    @PostMapping
    public ResponseEntity<?> cadastraRodada(@RequestBody RodadaDTO dto){
        service.cadastrarRodada(dto);
        //TODO: Retornar o id da escala
        //TODO: deve seguir uma ordem de rodadas para que o peso n fique errado (Ex errado: 09/05 (12) - 24/05 (9))
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
