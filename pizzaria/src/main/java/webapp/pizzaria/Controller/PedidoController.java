package webapp.pizzaria.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webapp.pizzaria.Model.Pedido;
import webapp.pizzaria.Model.Produto;
import webapp.pizzaria.Repository.PedidoRepository;
import webapp.pizzaria.Repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/enviar-pedido")
public class PedidoController {

    private final PedidoRepository pr;
    private final ProdutoRepository prr;

    @Autowired
    public PedidoController(PedidoRepository pr, ProdutoRepository prr) {
        this.pr = pr;
        this.prr = prr;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        Iterable<Produto> produtosIterable = prr.findAll();
        List<Produto> produtos = new ArrayList<>();
        produtosIterable.forEach(produtos::add);
        model.addAttribute("produtos", produtos);
        return "formulario-pedido";
    }

    @PostMapping("/registrar")
    public String registrarPedido(@RequestParam Map<String, String> parametros) {
        LocalDateTime agora = LocalDateTime.now();
        for (Map.Entry<String, String> entry : parametros.entrySet()) {
            String paramName = entry.getKey();
            if (paramName.startsWith("quantidade_produto")) {
                String produtoId = paramName.substring("quantidade_produto".length());
                String quantidadeStr = entry.getValue();
                int quantidade = Integer.parseInt(quantidadeStr);
                if (quantidade > 0) {
                    Pedido pedido = new Pedido();
                    pedido.setProduto(produtoId);
                    pedido.setQuantidade(quantidade);
                    pedido.setDataHoraPedido(agora);
                    String endereco = parametros.get("endereco");
                    if (endereco != null && !endereco.isEmpty()) {
                        pedido.setEndereco(endereco);
                        pedido.setEntrega("sim");
                    } else {
                        pedido.setEndereco("Retirar na Pizzaria");
                        pedido.setEntrega("não");
                    }
                    pr.save(pedido);
                }
            }
        }
        return "index";
    }

    @PostMapping("/pesquisar")
    public String pesquisarPedidos(@RequestParam Map<String, String> parametros, Model model) {
        String produto = parametros.get("produto");
        String endereco = parametros.get("endereco");
        String entrega = parametros.get("entrega");
        String idString = parametros.get("id");
        Long id = null;
        if (idString != null && !idString.isEmpty()) {
            try {
                id = Long.parseLong(idString);
            } catch (NumberFormatException e) {
                
            }
        }

        List<Pedido> pedidos = new ArrayList<>();
        if (produto != null && !produto.isEmpty()) {
            pedidos = pr.findByProduto(produto);
        } else if (endereco != null && !endereco.isEmpty()) {
            pedidos = pr.findByEndereco(endereco);
        } else if (entrega != null && !entrega.isEmpty()) {
            pedidos = pr.findByEntrega(entrega);
        } else if (id != null) {
            Optional<Pedido> pedido = pr.findById(id);
            pedido.ifPresent(pedidos::add);
        } else {
            pedidos = (List<Pedido>) pr.findAll();
        }

        model.addAttribute("pedidos", pedidos);

        return "pesquisa/resultado-pesquisa-pedidos";
    }
}
