package webapp.pizzaria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webapp.pizzaria.Model.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p " +
           "WHERE (:produto IS NULL OR p.produto LIKE %:produto%) " +
           "AND (:endereco IS NULL OR p.endereco LIKE %:endereco%) " +
           "AND (:entrega IS NULL OR p.entrega = :entrega) " +
           "AND (:id IS NULL OR p.id = :id)")
    List<Pedido> findByParameters(@Param("produto") String produto,
                                  @Param("endereco") String endereco,
                                  @Param("entrega") String entrega,
                                  @Param("id") Long id);
}