package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.RegisterDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.UsuarioResponseDTO;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criar(RegisterDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new RegraDeNegocioException("E-mail já cadastrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.senha()));
        usuario.setRole(dto.role());
        usuario.setAtivo(true);

        return UsuarioResponseDTO.de(usuarioRepository.save(usuario));
    }

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::de)
                .toList();
    }

    @Transactional
    public void alterarStatus(UUID id, boolean ativo) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
        usuario.setAtivo(ativo);
    }

}
