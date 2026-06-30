package br.com.paulo.repositories;

import br.com.paulo.entities.afastamentos.Afastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AfastamentoRepository extends JpaRepository<Afastamento, UUID> {

}
