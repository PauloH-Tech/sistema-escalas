package br.com.paulo.repositories;

import br.com.paulo.entities.rodadas.RodadaEscala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RodadaRepository extends JpaRepository<RodadaEscala, Long> {
    RodadaEscala findByNumeroRodada(Long rodada);

    @Query("SELECT MAX(p.numeroRodada) FROM RodadaEscala p")
    int findMaxNumeroRodada();
}
