package br.com.paulo.escalas.services;

import br.com.paulo.email.services.EmailOutboxService;
import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import br.com.paulo.escalas.entities.escalas.EscalaExtraDTO;
import br.com.paulo.escalas.entities.militares.Militar;
import br.com.paulo.escalas.entities.militares.MilitarPrioridadeDTO;
import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import br.com.paulo.escalas.exceptions.MilitarInativoException;
import br.com.paulo.escalas.exceptions.MilitarNotFoundException;
import br.com.paulo.escalas.exceptions.RegraDeNegocioException;
import br.com.paulo.escalas.exceptions.RodadaNotFoundException;
import br.com.paulo.escalas.repositories.EscalaExtraRepository;
import br.com.paulo.escalas.repositories.MilitarRepository;
import br.com.paulo.escalas.repositories.RodadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    private EmailOutboxService emailOutboxService;

    @Value("${send-email}")
    private boolean sendEmail;


    @Transactional
    public List<EscalaExtra> cadastrar(EscalaExtraDTO dto){
        RodadaEscala rodada = rodadaRepository.findById(dto.rodadaId())
                .orElseThrow(() -> new RegraDeNegocioException("Rodada " + dto.rodadaId() + "não encontrada"));

        List<EscalaExtra> criadas = new ArrayList<>();
        for (UUID militarId : dto.militarIds()){
            if (escalaRepository.existsByMilitarIdAndRodadaId(militarId, rodada.getId())) {
                throw new RegraDeNegocioException("Militar já está escalado nesta rodada");
            }
            Militar militar = militarRepository.findById(militarId)
                    .orElseThrow(() -> new MilitarNotFoundException("Militar " + militarId + "não encontrado"));
            if (!Boolean.TRUE.equals(militar.getSt_ativo())) {
                throw new MilitarInativoException("Militar " + militar.getNome() + "está inativo");
            }

            EscalaExtra escala = new EscalaExtra();
            escala.setMilitar(militar);
            escala.setRodada(rodada);
            criadas.add(escalaRepository.save(escala));

            if(sendEmail) {
                emailOutboxService.salvarEmail(escala);
            }
        }
        return criadas;
    }


    public List<MilitarPrioridadeDTO> listarMilitaresOrdenados(LocalDate date) {
        boolean existsRodada = rodadaRepository.existsByData(date);
        if (!existsRodada) {
            throw new RodadaNotFoundException("Rodada com a data " + date + " não encontrada no banco de dados");
        }
        return escalaRepository.listaOrdenada(date);
    }

    public void deletarEscalado(UUID id) {
        boolean exists = escalaRepository.existsById(id);
        if (!exists) {
            throw new RodadaNotFoundException("Rodada com id " + id.toString() + " não encontrada no banco de dados");
        }
        escalaRepository.deleteById(id);

    }
}
