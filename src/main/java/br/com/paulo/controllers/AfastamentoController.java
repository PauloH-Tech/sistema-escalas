package br.com.paulo.controllers;

import br.com.paulo.entities.afastamentos.Afastamento;
import br.com.paulo.entities.afastamentos.AfastamentoDTO;
import br.com.paulo.repositories.MilitarRepository;
import br.com.paulo.services.AfastamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/fastamento")
public class AfastamentoController {

    @Autowired
    private MilitarRepository militarRepository;

    @Autowired
    private AfastamentoService afastamentoService;


    @PostMapping("/afastamento/{idMilitar}")
    public ResponseEntity<?> afastarMilitar(@PathVariable UUID idMilitar, @RequestBody AfastamentoDTO body){
        afastamentoService.cadastrarAfastamento(idMilitar, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
