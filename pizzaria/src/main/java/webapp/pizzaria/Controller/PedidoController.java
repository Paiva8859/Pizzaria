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
    public String mostrarFormulario(org.springframework.ui.Model model) {
        // Obter a lista de produtos do banco de dados
        Iterable<Produto> produtosIterable = prr.findAll();
        List<Produto> produtos = new ArrayList<>();

        // Converter Iterable para List
        produtosIterable.forEach(produtos::add);

        // Adicionar a lista de produtos ao modelo
        model.addAttribute("produtos", produtos);

        return "formulario-pedido";
    }

    @PostMapping
    public String enviarPedido(@RequestParam Map<String, String> parametros) {
        LocalDateTime agora = LocalDateTime.now(); // Obtém a data e hora atuais

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

                    // Verifica se o endereço foi fornecido
                    String endereco = parametros.get("endereco");
                    if (endereco != null && !endereco.isEmpty()) {
                        pedido.setEndereco(endereco);
                        pedido.setEntrega("sim");
                    } else {
                        pedido.setEndereco("Comer na Pizzaria");
                        pedido.setEntrega("não");
                    }

                    pr.save(pedido);
                }
            }
        }
        return "index";
    }

    @GetMapping("/pesquisar-pedidos")
    public String mostrarFormularioPesquisa() {
        return "formulario-pesquisa-pedidos";
    }

    @PostMapping("/pesquisar-pedidos")
    public String pesquisarPedidos(@RequestParam(value = "produto", required = false) String produto,
            @RequestParam(value = "entrega", required = false) Boolean entrega,
            @RequestParam(value = "endereco", required = false) String endereco,
            @RequestParam(value = "id", required = false) Long id,
            Model model) {
        // Realizar a pesquisa com base nos parâmetros fornecidos
        List<Pedido> pedidos = pr.find(produto, entrega,
                endereco, id);

        // Adicionar os resultados da pesquisa ao modelo
        model.addAttribute("pedidos", pedidos);

        return "resultado-pesquisa-pedidos";
    }

}
