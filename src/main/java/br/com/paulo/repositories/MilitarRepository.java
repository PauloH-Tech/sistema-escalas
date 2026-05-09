package br.com.paulo.repositories;

import br.com.paulo.entities.militares.Militar;
import br.com.paulo.entities.militares.MilitarPrioridadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitarRepository extends JpaRepository<Militar, Long> {


    @Query(value = """
            select b.id,
                   b.nome,
                   count(a.militar_id) as qtd_escalas_feitas,
                   sum(c.nr_rodada) as soma
              from escala_extra a,
                   militares b,
                   rodada_escala c
             where a.militar_id = b.id
               and a.rodada_id   = c.id
             group by b.id, b.nome
             order by qtd_escalas_feitas asc, soma asc
            """, nativeQuery = true)
    List<MilitarPrioridadeDTO> listaOrdenada();

}
