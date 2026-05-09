package br.com.paulo.repositories;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarPrioridadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitarRepository extends JpaRepository<Militar, Long> {


    @Query(value = """
            select b.id,
                   b.nome,
                   count(a.militar_id) as qtd_escalas_feitas,
                   sum(c.nr_rodada) as soma
              from escala_extra a,
                   militares b,
                   rodada_escala c
             where a.militar_id = b.id
               and a.rodada_id   = c.id
             group by b.id, b.nome
             order by qtd_escalas_feitas asc, soma asc
            """, nativeQuery = true)
    List<MilitarPrioridadeDTO> listaOrdenada();

//    @Query(value = """
//            SELECT m.id,
//                   m.nome,
//                   COUNT(e.id)                        AS qtd_escalas,
//                   COALESCE(SUM(r.nr_rodada), 0)      AS soma,
//
//                   -- quantas rodadas esse militar estava disponível (sem afastamento ativo)
//                   (
//                       SELECT COUNT(*)
//                         FROM rodada_escala ro
//                        WHERE NOT EXISTS (
//                            SELECT 1
//                              FROM afastamentos a
//                             WHERE a.militar_id = m.id
//                               AND ro.data BETWEEN a.inicio AND a.fim
//                        )
//                   ) AS rodadas_disponiveis,
//
//                   -- taxa = escalas feitas / rodadas disponíveis (para ordenação proporcional)
//                   -- NULLIF evita divisão por zero para militares sem nenhuma rodada disponível
//                   CASE
//                       WHEN (
//                           SELECT COUNT(*)
//                             FROM rodada_escala ro
//                            WHERE NOT EXISTS (
//                                SELECT 1
//                                  FROM afastamentos a
//                                 WHERE a.militar_id = m.id
//                                   AND ro.data BETWEEN a.inicio AND a.fim
//                            )
//                       ) = 0 THEN 0
//                       ELSE COUNT(e.id) * 1.0 / (
//                           SELECT COUNT(*)
//                             FROM rodada_escala ro
//                            WHERE NOT EXISTS (
//                                SELECT 1
//                                  FROM afastamentos a
//                                 WHERE a.militar_id = m.id
//                                   AND ro.data BETWEEN a.inicio AND a.fim
//                            )
//                       )
//                   END AS taxa_escalas
//
//              FROM militares m
//              LEFT JOIN escala_extra e  ON e.militar_id = m.id
//              LEFT JOIN rodada_escala r ON r.id = e.rodada_id
//
//             WHERE m.st_ativo = true
//
//               -- exclui quem está afastado hoje (não aparece na fila do escalador)
//               AND NOT EXISTS (
//                   SELECT 1
//                     FROM afastamentos a
//                    WHERE a.militar_id = m.id
//                      AND a.inicio <= CURRENT_DATE
//                      AND a.fim    >= CURRENT_DATE
//               )
//
//             GROUP BY m.id, m.nome
//
//             -- camada 1: menor taxa primeiro (proporcionalmente menos escalado)
//             -- camada 2: menor soma primeiro (escalas mais antigas = vai na frente)
//             -- camada 3: data_ingresso como desempate final (mais tempo de serviço = prioridade)
//             ORDER BY taxa_escalas ASC, soma ASC, m.data_ingresso ASC
//            """, nativeQuery = true)
//    List<MilitarPrioridadeDTO> listaOrdenada();

}
