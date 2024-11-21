package servlet;

import dao.CarrinhoDao;
import dao.EnderecoDao;
import dao.PedidoDao;
import dao.PedidoItemDao;
import model.Endereco;
import model.ItemCarrinho;
import model.Pedido;
import model.PedidoItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/resumo-pedido")
public class ResumoPedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");
            if (clienteId == null) {
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            // Busca os endereços do cliente
            EnderecoDao enderecoDao = new EnderecoDao();
            List<Endereco> enderecos = enderecoDao.buscarEnderecosPorClienteId(clienteId);
            req.setAttribute("enderecos", enderecos);

            // Busca os itens do carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho();
            req.setAttribute("itensCarrinho", itensCarrinho);

            // Calcula subtotal e total
            double subtotal = 0.0;
            for (ItemCarrinho item : itensCarrinho) {
                subtotal += item.getTenis().getPreco() * item.getQuantidade();
            }
            double frete = 30.0;
            double total = subtotal + frete;
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("frete", frete);
            req.setAttribute("total", total);

            // Redireciona para a página de resumo
            req.getRequestDispatcher("/Resumo-Pedido/resumo-pedido.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar dados para o resumo do pedido.");
        }
    }

    // Método para concluir a compra
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            if (clienteId == null) {
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            // Recupera o endereço de entrega (selecionado na tela de resumo)
            Endereco enderecoEntrega = (Endereco) req.getAttribute("enderecoSelecionado"); // Ou de sessão

            // Cria o pedido no banco
            PedidoDao pedidoDao = new PedidoDao();
            Pedido pedido = new Pedido(clienteId, enderecoEntrega.getId());
            int pedidoId = pedidoDao.criarPedido(pedido); // Pedido é inserido e retorna o ID

            // Recupera os itens do carrinho e cria o PedidoItem
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho();
            PedidoItemDao pedidoItemDao = new PedidoItemDao();

            for (ItemCarrinho item : itensCarrinho) {
                PedidoItem pedidoItem = new PedidoItem(pedidoId, item.getTenis().getId(), item.getQuantidade(), item.getTenis().getPreco());
                pedidoItemDao.adicionarItensAoPedido(List.of(pedidoItem));
            }

            // Exibe confirmação para o cliente
            req.setAttribute("sucesso", "Pedido número " + pedidoId + " realizado com sucesso! Total: R$" + pedido.getValorTotal());
            req.getRequestDispatcher("/Cliente-Pedidos/cliente-pedidos.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erro", "Erro ao concluir pedido. Tente novamente.");
            req.getRequestDispatcher("/Resumo-Pedido/resumo-pedido.jsp").forward(req, resp);
        }
    }
}
