package webapp.pizzaria.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
     @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/cadastro-funcionario-page")
    public String cadastroFuncionarioPage() {
        return "internas/cadastro-funcionario";
    }

    @GetMapping("/pesquisar-funcionario-page")
    public String pesquisarFuncionariosPage() {
        return "internas/pesquisar-funcionario";
    }

    @GetMapping("/pesquisar-pedido-page")
    public String pesquisarPedidosPage() {
        return "internas/pesquisar-pedido";
    }

    @GetMapping("/realizar-pedido-page")
    public String realizarPedidosPage() {
        return "internas/realizar-pedido";
    }
}
