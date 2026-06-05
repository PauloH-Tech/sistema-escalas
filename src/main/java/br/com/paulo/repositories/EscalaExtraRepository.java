package br.com.paulo.repositories;

import br.com.paulo.entities.escalas.EscalaExtra;
import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarPrioridadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EscalaExtraRepository extends JpaRepository<EscalaExtra, UUID> {


//    @Query(value = """
//            select b.id,
//                   b.nome,
//                   count(a.militar_id) as qtd_escalas_feitas,
//                   /*sum(c.nr_rodada) as soma*/
//              from escala_extra a,
//                   militares b,
//                   rodada_escala c
//             where a.militar_id = b.id
//               and a.rodada_id   = c.id
//             group by b.id, b.nome
//             /*order by qtd_escalas_feitas asc, soma asc/*
//            """, nativeQuery = true)
//    List<Militar> listaOrdenada();


    @Query(value = """
            SELECT m.id,
                   m.nm_militar,
                   m.graduacao,
                   MAX(r.data) AS ultima_escala,
                   a.tp_afastamento AS tipo_afastamento,
                   COUNT(
                       CASE
                           WHEN r.data >= CURRENT_DATE - INTERVAL '90 days'
                           THEN 1
                       END
                   ) AS qt_escalas
              FROM militares m
              LEFT JOIN escala_extra e
                ON e.militar_id = m.id
              LEFT JOIN rodada_escala r
                ON r.id = e.rodada_id
              LEFT JOIN afastamento a
                ON a.militar_id = m.id
               AND :dataRodada BETWEEN a.dt_inicio AND a.dt_fim
             GROUP BY
                   m.id,
                   m.nm_militar,
                   m.graduacao,
                   a.tp_afastamento
             ORDER BY
                   ultima_escala ASC NULLS FIRST,
                   qt_escalas,
                   m.graduacao;
            """, nativeQuery = true)
    List<MilitarPrioridadeDTO> listaOrdenada(
            @Param(value = "dataRodada")LocalDate dataRodada
            );

}
