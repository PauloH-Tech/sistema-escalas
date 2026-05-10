package br.com.paulo.controllers;

import br.com.paulo.entities.afastamentos.Afastamento;
import br.com.paulo.entities.afastamentos.AfastamentoDTO;
import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarDTO;
import br.com.paulo.repositories.AfastamentoRepository;
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
        Militar salvo = militarService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/afastamento")
    public ResponseEntity<?> afastarMilitar(@RequestBody AfastamentoDTO body){
        return ResponseEntity.status(HttpStatus.CREATED).body(afastamentoService.cadastrarAfastamento(body));
    }
}
