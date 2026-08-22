package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.afastamentos.Afastamento;
import br.com.paulo.escalas.entities.afastamentos.AfastamentoDTO;
import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.exceptions.MilitarInativoException;
import br.com.paulo.escalas.exceptions.MilitarNotFoundException;
import br.com.paulo.escalas.repositories.AfastamentoRepository;
import br.com.paulo.escalas.repositories.MilitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AfastamentoService {

    @Autowired
    private AfastamentoRepository afastamentoRepository;

    @Autowired
    private MilitarRepository militarRepository;


    public void cadastrarAfastamento(UUID idMilitar, AfastamentoDTO dto){
        Militar militar = militarRepository.findById(idMilitar)
                .orElseThrow(() -> new MilitarNotFoundException(idMilitar.toString()));
        if (!militar.getSt_ativo()) {
            throw new MilitarInativoException("Não foi possível criar um afastamento pois este militar está inativo");
        }

        Afastamento novoAfastamento = new Afastamento();
        novoAfastamento.setMilitar(militar);
        novoAfastamento.setTpAfastamento(dto.tpAfastamento());
        novoAfastamento.setDtInicio(dto.dtInicio());
        novoAfastamento.setDtFim(dto.dtFim());

        afastamentoRepository.save(novoAfastamento);
    }


    public List<Afastamento> listar() {
        return afastamentoRepository.findAll();
    }
}
