package br.com.paulo.services;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarDTO;
import br.com.paulo.repositories.MilitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MilitarService {

    @Autowired
    private MilitarRepository repository;


    public List<Militar> listarMilitares() {
        return repository.findAllByOrderByGraduacaoDesc();
    }

    public void cadastrar(MilitarDTO dto) {
        Militar militar = new Militar();
        militar.setNome(dto.nome());
        militar.setSt_ativo(dto.stAtivo());
        militar.setGraduacao(dto.graduacao());

        repository.save(militar);
    }

    public void atualizar(UUID id, MilitarDTO dto) {
        Militar militar = repository.findById(id).orElseThrow(() -> new RuntimeException("Militar não encontrado"));

        if (militar != null){
            militar.setNome(dto.nome());
            militar.setGraduacao(dto.graduacao());
            militar.setSt_ativo(dto.stAtivo());

            repository.save(militar);
        }
    }

    public void deletar(UUID id) {
        Militar militar = repository.findById(id).orElseThrow(() -> new RuntimeException("Militar não encontrado"));
        if (militar != null) {
            repository.deleteById(id);
        }
    }
}
