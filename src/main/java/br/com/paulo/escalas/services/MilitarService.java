package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.militares.MilitarDTO;
import br.com.paulo.escalas.exceptions.MilitarNotFoundException;
import br.com.paulo.escalas.repositories.MilitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MilitarService {

    @Autowired
    private MilitarRepository repository;


    public List<Militar> listarMilitaresAtivos() {
        return repository.buscaTodosMilitaresAtivos();
    }

    public UUID cadastrar(MilitarDTO dto) {
        Militar militar = new Militar();
        militar.setNome(dto.nome());
        militar.setSt_ativo(dto.stAtivo());
        militar.setGraduacao(dto.graduacao());
        militar.setEmail(dto.email());

        repository.save(militar);

        return militar.getId();
    }

    public void atualizar(UUID id, MilitarDTO dto) {
        Militar militar = repository.findById(id).orElseThrow(
                () -> new MilitarNotFoundException("Militar com id " + id.toString() + " não encontrado no banco de dados"));

        if (militar != null){
            militar.setNome(dto.nome());
            militar.setGraduacao(dto.graduacao());
            militar.setSt_ativo(dto.stAtivo());
            militar.setEmail(dto.email());

            repository.save(militar);
        }
    }

    //TODO: PSQLException causa erro de constraint se tiver escalado ja
    public void inativar(UUID id) {
        Militar militar = repository.findById(id).orElseThrow(
                () -> new MilitarNotFoundException("Militar com id " + id.toString() + " não encontrado no banco de dados"));
        if (militar != null) {
            militar.setSt_ativo(false);
            repository.save(militar);
        }
    }

    public List<Militar> listarMilitaresInativos() {
        return repository.buscaTodosMilitaresInativos();
    }

    public void ativar(UUID id) {
        Militar militar = repository.findById(id).orElseThrow(
                () -> new MilitarNotFoundException("Militar com id " + id.toString() + " não encontrado no banco de dados")
        );
        if (militar != null) {
            System.out.println("ativando militar");
            militar.setSt_ativo(true);
            repository.save(militar);
        }
    }
}
