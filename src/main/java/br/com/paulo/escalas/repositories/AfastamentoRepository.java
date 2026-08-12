package br.com.paulo.escalas.repositories;

import br.com.paulo.escalas.entities.afastamentos.Afastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AfastamentoRepository extends JpaRepository<Afastamento, UUID> {

}
