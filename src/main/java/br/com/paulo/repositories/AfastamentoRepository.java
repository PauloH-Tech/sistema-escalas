package br.com.paulo.repositories;

import br.com.paulo.entities.afastamentos.Afastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfastamentoRepository extends JpaRepository<Afastamento, Long> {
}
