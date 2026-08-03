package br.com.paulo.email.repositories;

import br.com.paulo.email.entities.EmailOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailOutbox, UUID> {


    @Query(value = "select e from EmailOutbox e where e.status = 'PENDENTE' or (e.status = 'ERRO' and e.tentativas < 4) limit 1")
    List<EmailOutbox> findEmailsPendentes();
}
