package br.com.paulo.services;

import br.com.paulo.entities.afastamentos.Afastamento;
import br.com.paulo.entities.afastamentos.AfastamentoDTO;
import br.com.paulo.entities.militares.Militar;
import br.com.paulo.repositories.AfastamentoRepository;
import br.com.paulo.repositories.MilitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AfastamentoService {

    @Autowired
    private AfastamentoRepository afastamentoRepository;

    @Autowired
    private MilitarRepository militarRepository;


    public Afastamento cadastrarAfastamento(AfastamentoDTO dto){
        Militar militar = militarRepository.getReferenceById(dto.militarId());

        Afastamento novoAfastamento = new Afastamento();
        novoAfastamento.setMilitar(militar);
        novoAfastamento.setDtInicio(dto.dtInicio());
        novoAfastamento.setDtFim(dto.dtFim());

        return afastamentoRepository.save(novoAfastamento);
    }


}
