package webapp.pizzaria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import webapp.pizzaria.Model.Funcionario;
import webapp.pizzaria.Repository.FuncionarioRepository;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @PostMapping("cadastro-fun")
    public String postCadastroAdm(Funcionario fun) {
        fr.save(fun);
        
        return "index";
    }
    

}
