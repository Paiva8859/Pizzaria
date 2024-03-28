package webapp.pizzaria.Repository;

import org.springframework.data.repository.CrudRepository;
import webapp.pizzaria.Model.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String>{
        
}