package webapp.pizzaria.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webapp.pizzaria.Model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {
    List<Pedido> findByEntrega(String entrega);
    List<Pedido> findByEndereco(String endereco);
    List<Pedido> findByProduto(String produto);
}
