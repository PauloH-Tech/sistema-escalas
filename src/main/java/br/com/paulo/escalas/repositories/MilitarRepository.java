package br.com.paulo.escalas.repositories;

import br.com.paulo.escalas.entities.militares.Militar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MilitarRepository extends JpaRepository<Militar, UUID> {

    @Query(value = "select m from Militar m where m.st_ativo = true order by m.graduacao desc")
    List<Militar> buscaTodosMilitaresAtivos();

    @Query(value = "select m from Militar m where m.st_ativo = false order by m.graduacao desc")
    List<Militar> buscaTodosMilitaresInativos();
}
