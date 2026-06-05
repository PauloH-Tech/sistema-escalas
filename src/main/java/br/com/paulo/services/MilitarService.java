package br.com.paulo.services;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarDTO;
import br.com.paulo.repositories.MilitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MilitarService {

    @Autowired
    private MilitarRepository repository;


    public List<Militar> listarMilitares() {
        return repository.findAll();
    }

    public void cadastrar(MilitarDTO dto) {
        Militar militar = new Militar();
        militar.setNome(dto.nome());
        militar.setSt_ativo(dto.stAtivo());
        militar.setGraduacao(dto.graduacao());

        repository.save(militar);
    }
}
