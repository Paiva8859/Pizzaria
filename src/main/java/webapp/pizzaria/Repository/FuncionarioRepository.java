package webapp.pizzaria.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webapp.pizzaria.Model.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, String> {
    @Query("SELECT f FROM Funcionario f " +
            "WHERE (:nome IS NULL OR f.nome LIKE %:nome%) " +
            "AND (:cpf IS NULL OR f.cpf LIKE %:cpf%) " +
            "AND (:email IS NULL OR f.email LIKE %:email%)")
    List<Funcionario> findByParameters(@Param("nome") String nome,
            @Param("cpf") String cpf,
            @Param("email") String email);
}