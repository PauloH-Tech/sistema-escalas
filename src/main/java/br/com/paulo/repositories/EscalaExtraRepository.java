package br.com.paulo.repositories;

import br.com.paulo.entities.escalas.EscalaExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscalaExtraRepository extends JpaRepository<EscalaExtra, Long> {
}
