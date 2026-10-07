package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.militares.MilitarDTO;
import br.com.paulo.escalas.exceptions.MilitarNotFoundException;
import br.com.paulo.escalas.repositories.MilitarRepository;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MilitarService {

    @Autowired
    private MilitarRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Militar> listarMilitaresAtivos() {
        return repository.buscaTodosMilitaresAtivos();
    }

    @Transactional
    public UUID cadastrar(MilitarDTO dto) {
        Militar militar = new Militar();
        militar.setNome(dto.nome());
        militar.setSt_ativo(dto.stAtivo());
        militar.setGraduacao(dto.graduacao());
        repository.save(militar);
        return militar.getId();
    }

    @Transactional
    public void atualizar(UUID id, MilitarDTO dto) {
        Militar militar = buscar(id);
        militar.setNome(dto.nome());
        militar.setGraduacao(dto.graduacao());
        militar.setSt_ativo(dto.stAtivo());
        // o acesso ao app acompanha a situação do militar
        usuarioRepository.findByMilitarId(id).ifPresent(u -> u.setAtivo(dto.stAtivo()));
    }

    @Transactional
    public void inativar(UUID id) {
        Militar militar = buscar(id);
        militar.setSt_ativo(false);
        usuarioRepository.findByMilitarId(id).ifPresent(u -> u.setAtivo(false));
    }

    public List<Militar> listarMilitaresInativos() {
        return repository.buscaTodosMilitaresInativos();
    }

    @Transactional
    public void ativar(UUID id) {
        Militar militar = buscar(id);
        militar.setSt_ativo(true);
        usuarioRepository.findByMilitarId(id).ifPresent(u -> u.setAtivo(true));
    }

    private Militar buscar(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> new MilitarNotFoundException("Militar com id " + id + " não encontrado no banco de dados"));
    }
}
