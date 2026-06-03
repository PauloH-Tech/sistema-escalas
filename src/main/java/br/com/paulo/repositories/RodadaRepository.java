package br.com.paulo.repositories;

import br.com.paulo.entities.rodadas.RodadaEscala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RodadaRepository extends JpaRepository<RodadaEscala, UUID> {

//    @Query("SELECT MAX(p.numeroRodada) FROM RodadaEscala p")
//    int findMaxNumeroRodada();
}
