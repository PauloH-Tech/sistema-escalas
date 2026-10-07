package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.rodadas.RodadaDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.exceptions.RodadaNotFoundException;
import br.com.paulo.escalas.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RodadaService {

    @Autowired
    private RodadaRepository repository;

    public void cadastrarRodada(RodadaDTO dto){
        if (repository.existsByData(dto.data())) {
            throw new RegraDeNegocioException("Já existe uma rodada para o dia " + dto.data());
        }
        RodadaEscala novaRodada = new RodadaEscala();
        novaRodada.setData(dto.data());

        repository.save(novaRodada);
    }

    public List<RodadaEscala> listarProximasRodadas() {
        return repository.findNextRodadas();
    }

    public List<RodadaEscala> listarTodas() {
        return repository.findAll(Sort.by("data").descending());
    }

    public void deletar(UUID id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RodadaNotFoundException(e.getMessage());
        }
    }

    public List<RodadaEscala> rodadasDoMilitar(UUID militar) {
        return repository.findRodadasDoMilitar(militar);
    }
}
