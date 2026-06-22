package br.com.paulo.repositories;

import br.com.paulo.entities.militares.Militar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MilitarRepository extends JpaRepository<Militar, UUID> {
    List<Militar> findAllByOrderByGraduacaoDesc();
}
