package br.com.paulo.services;

import br.com.paulo.entities.escalas.EscalaExtra;
import br.com.paulo.entities.escalas.EscalaExtraDTO;
import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.rodadas.RodadaEscala;
import br.com.paulo.repositories.EscalaExtraRepository;
import br.com.paulo.repositories.MilitarRepository;
import br.com.paulo.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EscalaExtraService {


    @Autowired
    private EscalaExtraRepository escalaRepository;
    @Autowired
    private MilitarRepository militarRepository;
    @Autowired
    private RodadaRepository rodadaRepository;


    @Transactional
    public EscalaExtra cadastrar(EscalaExtraDTO escalaExtra){
        Militar militar = militarRepository.getReferenceById(escalaExtra.militarId());
        RodadaEscala rodadaEscala = rodadaRepository.getReferenceById(escalaExtra.rodadaId());

        EscalaExtra escala = new EscalaExtra();
        escala.setMilitar(militar);
        escala.setRodada(rodadaEscala);
        return escalaRepository.save(escala);
    }


}
