package webapp.pizzaria.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import webapp.pizzaria.Model.Funcionario;
import webapp.pizzaria.Repository.FuncionarioRepository;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @PostMapping("cadastro-fun")
    public String postCadastroFun(Funcionario fun) {
        fr.save(fun);
        
        return "index";
    }
    
    @GetMapping("/pesquisar-funcionarios")
    public String mostrarFormularioPesquisa(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "pesquisa-funcionarios";
    }

    @PostMapping("/pesquisar-funcionarios")
    public String pesquisarFuncionarios(@ModelAttribute Funcionario funcionario, Model model) {
        List<Funcionario> funcionarios = fr.findByParameters(
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getEmail());
        model.addAttribute("funcionarios", funcionarios);
        return "pesquisa/resultado-pesquisa-funcionario";
    }
}
