package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.usuarios.UserRole;
import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.entities.usuarios.dtos.AtualizarAcessoDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.ConcederAcessoDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.RegisterDTO;
import br.com.paulo.escalas.entities.usuarios.dtos.UsuarioResponseDTO;
import br.com.paulo.escalas.exceptions.MilitarNotFoundException;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.repositories.MilitarRepository;
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
    private final MilitarRepository militarRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criar(RegisterDTO dto) {
        String email = normalizarEmail(dto.email());
        validarEmailDisponivel(email);
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(dto.senha()));
        usuario.setRole(dto.role());
        usuario.setAtivo(true);
        usuario.setMilitar(buscarMilitar(dto));

        return UsuarioResponseDTO.de(usuarioRepository.save(usuario));
    }

    private Militar buscarMilitar(RegisterDTO dto) {
        if (dto.militarId() == null) {
            if (dto.role() == UserRole.USER) {
                throw new RegraDeNegocioException("Usuário comum precisa estar vinculado a um militar");
            }
            return null;
        }
        if (usuarioRepository.existsByMilitarId(dto.militarId())) {
            throw new RegraDeNegocioException("Militar já possui usuário cadastrado");
        }
        return militarRepository.findById(dto.militarId())
                .orElseThrow(() -> new MilitarNotFoundException("Militar %s não encontrado".formatted(dto.militarId())));
    }

    /**
     * Concede acesso ao app a um militar já cadastrado. O usuário nasce sem senha;
     * o militar define nome e senha no primeiro acesso.
     */
    @Transactional
    public UsuarioResponseDTO concederAcesso(ConcederAcessoDTO dto) {
        if (usuarioRepository.existsByMilitarId(dto.militarId())) {
            throw new RegraDeNegocioException("Militar já possui acesso ao app");
        }
        Militar militar = militarRepository.findById(dto.militarId())
                .orElseThrow(() -> new MilitarNotFoundException("Militar %s não encontrado".formatted(dto.militarId())));
        String email = normalizarEmail(dto.email());
        validarEmailDisponivel(email);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setRole(dto.role() != null ? dto.role() : UserRole.USER);
        usuario.setAtivo(Boolean.TRUE.equals(militar.getSt_ativo()));
        usuario.setMilitar(militar);
        // nome e senha ficam nulos até o primeiro acesso
        return UsuarioResponseDTO.de(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDTO atualizarAcesso(UUID id, AtualizarAcessoDTO dto) {
        Usuario usuario = buscar(id);
        String email = normalizarEmail(dto.email());
        if (!email.equals(usuario.getEmail())) {
            validarEmailDisponivel(email);
        }
        if (dto.role() == UserRole.USER && usuario.getMilitar() == null) {
            throw new RegraDeNegocioException("Usuário comum precisa estar vinculado a um militar");
        }
        usuario.setEmail(email);
        usuario.setRole(dto.role());
        return UsuarioResponseDTO.de(usuario);
    }

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::de)
                .toList();
    }

    @Transactional
    public void alterarStatus(UUID id, boolean ativo) {
        Usuario usuario = buscar(id);
        usuario.setAtivo(ativo);
    }

    private Usuario buscar(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));
    }

    private void validarEmailDisponivel(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraDeNegocioException("E-mail já cadastrado");
        }
    }

    private static String normalizarEmail(String email) {
        return email.trim().toLowerCase();
    }

}
