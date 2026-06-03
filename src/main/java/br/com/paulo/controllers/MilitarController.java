package br.com.paulo.controllers;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarDTO;
import br.com.paulo.services.AfastamentoService;
import br.com.paulo.services.MilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/militar")
public class MilitarController {

    @Autowired
    private MilitarService militarService;

    @Autowired
    private AfastamentoService afastamentoService;


    @GetMapping
    public ResponseEntity<List<Militar>> listaMilitar(){
        return ResponseEntity.ok(militarService.listarMilitares());
    }

    @PostMapping
    public ResponseEntity<?> cadatrarMilitar(@RequestBody MilitarDTO dto) {
        militarService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
