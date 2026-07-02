package br.com.paulo.controllers;

import br.com.paulo.entities.escalas.EscalaExtra;
import br.com.paulo.entities.escalas.EscalaExtraDTO;
import br.com.paulo.entities.militares.MilitarPrioridadeDTO;
import br.com.paulo.repositories.EscalaExtraRepository;
import br.com.paulo.services.EscalaExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/escala")
public class EscalaController {

    @Autowired
    private EscalaExtraService escalaService;

    @Autowired
    private EscalaExtraRepository escalaExtraRepository;


    @PostMapping
    public ResponseEntity<?> cadatrarEscala(@RequestBody EscalaExtraDTO body){
        escalaService.cadastrar(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<MilitarPrioridadeDTO>> proximosPoliciais(@PathVariable LocalDate date){
        List<MilitarPrioridadeDTO> listaOrdenada = escalaService.listarMilitaresOrdenados(date);
        return ResponseEntity.ok(listaOrdenada);
    }



}
