package br.com.paulo.escalas.repositories;

import br.com.paulo.escalas.entities.rodadas.RodadaEscala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RodadaRepository extends JpaRepository<RodadaEscala, UUID> {

    @Query(value = """
            SELECT re.*
            FROM rodada_escala re
            WHERE NOT EXISTS (
                SELECT 1
                FROM escala_extra ee
                WHERE ee.rodada_id = re.id
            )
            ORDER BY re.data;
            """, nativeQuery = true)
    List<RodadaEscala> findNextRodadas();

    boolean existsByData(LocalDate date);

    @Query(value = """
            SELECT re.*
                  FROM rodada_escala re
                  WHERE re.data >= CURRENT_DATE - INTERVAL '10 days'
                    AND EXISTS (
                        SELECT 1 FROM escala_extra ev
                        WHERE ev.rodada_id = re.id
                          AND ev.militar_id = :militarId
                    )
                  ORDER BY re.data
            """, nativeQuery = true)
    List<RodadaEscala> findRodadasDoMilitar(@Param("militarId") UUID militarId);

//    @Query("SELECT MAX(p.numeroRodada) FROM RodadaEscala p")
//    int findMaxNumeroRodada();
}
