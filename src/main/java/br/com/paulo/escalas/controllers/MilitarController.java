package br.com.paulo.escalas.controllers;

import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.militares.MilitarDTO;
import br.com.paulo.escalas.services.MilitarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/militar")
public class MilitarController {

    @Autowired
    private MilitarService service;

    @GetMapping
    public ResponseEntity<List<Militar>> listaMilitar(){
        return ResponseEntity.ok(service.listarMilitaresAtivos());
    }

    @GetMapping("/inativos")
    public ResponseEntity<?> listarInativos(){
        return ResponseEntity.ok(service.listarMilitaresInativos());
    }

    @PostMapping
    public ResponseEntity<?> cadatrarMilitar(@RequestBody MilitarDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMilitar(@PathVariable UUID id, @RequestBody MilitarDTO dto){
        service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> inativarMilitar(@PathVariable UUID id){
        service.inativar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<?> AtivarMilitar(@PathVariable UUID id){
        service.ativar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
