package br.com.paulo.repositories;

import br.com.paulo.entities.rodadas.RodadaEscala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RodadaRepository extends JpaRepository<RodadaEscala, UUID> {

    @Query(value = """
            SELECT *
            FROM rodada_escala re
            WHERE re.data > (
                SELECT MAX(r.data)
                FROM escala_extra ee
            	JOIN rodada_escala r on r.id = ee.rodada_id
            )
            ORDER BY re.data;
            """, nativeQuery = true)
    List<RodadaEscala> findNextRodadas();

    boolean existsByData(LocalDate date);

//    @Query("SELECT MAX(p.numeroRodada) FROM RodadaEscala p")
//    int findMaxNumeroRodada();
}
