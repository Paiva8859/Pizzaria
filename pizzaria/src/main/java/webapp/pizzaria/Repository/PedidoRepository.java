package webapp.pizzaria.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.pizzaria.Model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p " +
            "WHERE (:produto IS NULL OR p.produto = :produto) " +
            "AND (:entrega IS NULL OR p.entrega = :entrega) " +
            "AND (:endereco IS NULL OR p.endereco = :endereco) " +
            "AND (:id IS NULL OR p.id = :id)")
    List<Pedido> pesquisarPedidos(@Param("produto") String produto,
            @Param("entrega") Boolean entrega,
            @Param("endereco") String endereco,
            @Param("id") Long id);

    List<Pedido> find(String produto, Boolean entrega,
            String endereco, Long codPedido);
}
