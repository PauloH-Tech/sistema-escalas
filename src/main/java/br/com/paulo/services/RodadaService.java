package br.com.paulo.services;

import br.com.paulo.entities.rodadas.RodadaDTO;
import br.com.paulo.entities.rodadas.RodadaEscala;
import br.com.paulo.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
