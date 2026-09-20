package br.com.paulo.escalas.controllers;

import br.com.paulo.escalas.entities.usuarios.dtos.RegisterDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.UsuarioResponseDTO;
import br.com.paulo.escalas.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO criar(@RequestBody @Valid RegisterDTO dto) {
        return usuarioService.criar(dto);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioService.listar();
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarStatus(@PathVariable UUID id, @RequestParam boolean ativo) {
        usuarioService.alterarStatus(id, ativo);
    }
}
