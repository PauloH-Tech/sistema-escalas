package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.rodadas.RodadaDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import br.com.paulo.escalas.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RodadaService {

    @Autowired
    private RodadaRepository repository;

    public void cadastrarRodada(RodadaDTO dto){
        //int nrRodada = repository.findMaxNumeroRodada() + 1;

        RodadaEscala novaRodada = new RodadaEscala();
        novaRodada.setData(dto.data());
        //novaRodada.setNumeroRodada(nrRodada);

        repository.save(novaRodada);
    }

    public List<RodadaEscala> listarProximasRodadas() {
        return repository.findNextRodadas();
    }

    public List<RodadaEscala> listarTodas() {
        return repository.findAll();
    }

    public void deletar(UUID id) {
        repository.deleteById(id);
    }
}
