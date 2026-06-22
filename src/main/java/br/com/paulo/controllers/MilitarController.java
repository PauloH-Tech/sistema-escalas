package br.com.paulo.controllers;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarDTO;
import br.com.paulo.services.MilitarService;
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
        return ResponseEntity.ok(service.listarMilitares());
    }

    @PostMapping
    public ResponseEntity<?> cadatrarMilitar(@RequestBody MilitarDTO dto) {
        service.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarMilitar(@PathVariable UUID id, @RequestBody MilitarDTO dto){
        service.atualizar(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMilitar(@PathVariable UUID id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
