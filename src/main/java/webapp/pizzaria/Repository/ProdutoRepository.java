package webapp.pizzaria.Repository;

import org.springframework.data.repository.CrudRepository;
import webapp.pizzaria.Model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long>{
    
}