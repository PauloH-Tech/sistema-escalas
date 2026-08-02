package br.com.paulo.email.entities.repositories;

import br.com.paulo.email.entities.EmailOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailRepository extends JpaRepository<EmailOutbox, UUID> {


}
