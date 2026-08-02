package br.com.paulo.escalas.services;

import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import br.com.paulo.escalas.entities.escalas.EscalaExtraDTO;
import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.militares.MilitarPrioridadeDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import br.com.paulo.escalas.exceptions.RodadaNotFoundException;
import br.com.paulo.escalas.repositories.EscalaExtraRepository;
import br.com.paulo.escalas.repositories.MilitarRepository;
import br.com.paulo.escalas.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EscalaExtraService {


    @Autowired
    private EscalaExtraRepository escalaRepository;
    @Autowired
    private MilitarRepository militarRepository;
    @Autowired
    private RodadaRepository rodadaRepository;


    @Transactional
    public void cadastrar(EscalaExtraDTO escalados){
        RodadaEscala rodadaEscala = rodadaRepository.getReferenceById(escalados.rodadaId());

        for (UUID militarEscalado : escalados.militarIds()){
            Militar militar = militarRepository.getReferenceById(militarEscalado);

            EscalaExtra escala = new EscalaExtra();
            escala.setMilitar(militar);
            escala.setRodada(rodadaEscala);
            escalaRepository.save(escala);
        }
    }


    public List<MilitarPrioridadeDTO> listarMilitaresOrdenados(LocalDate date) {
        boolean existsRodada = rodadaRepository.existsByData(date);
        if (existsRodada) {
            return escalaRepository.listaOrdenada(date);
        }

        throw new RodadaNotFoundException(date.toString());
    }
}
