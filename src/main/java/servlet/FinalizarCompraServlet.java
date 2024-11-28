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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/finalizar-compra")
public class FinalizarCompraServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtém o ID do cliente da sessão
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");
            Integer enderecoId = (Integer) session.getAttribute("enderecoId");

            if (clienteId == null) {
                // Se o cliente não estiver logado, redireciona para a página de login
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            // Obtém os dados do formulário
            String formaPagamento = req.getParameter("formaPagamento");
            if (formaPagamento == null || formaPagamento.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Forma de pagamento não selecionada.");
                return;
            }

            // Passa a forma de pagamento para a JSP
            req.setAttribute("formaPagamento", formaPagamento);

            // Obtém o endereço selecionado
            EnderecoDao enderecoDao = new EnderecoDao();
            Endereco enderecoSelecionado = enderecoDao.buscaEnderecoPorID(enderecoId);
            if (enderecoSelecionado == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Endereço não encontrado.");
                return;
            }

            // Busca os itens do carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);
            if (itensCarrinho.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Carrinho vazio.");
                return;
            }

            // Calcula o valor total
            double subtotal = 0.0;
            for (ItemCarrinho item : itensCarrinho) {
                subtotal += item.getTenis().getPreco() * item.getQuantidade();
            }
            double frete = 30.0; // Valor do frete
            double total = subtotal + frete;

            // Cria o pedido
            Pedido pedido = new Pedido();
            pedido.setClienteId(clienteId);
            pedido.setEnderecoEntregaId(enderecoId);
            pedido.setDataPedido(new Date());
            pedido.setStatus("Pendente");
            pedido.setValorTotal(total);
            pedido.setFormaPagamento(formaPagamento);

            // Salva o pedido no banco
            PedidoDao pedidoDao = new PedidoDao();
            int pedidoId = pedidoDao.criarPedido(pedido);  // Salva o pedido e retorna o ID

            // Cria a lista de PedidoItems
            List<PedidoItem> pedidoItems = new ArrayList<>();
            for (ItemCarrinho item : itensCarrinho) {
                PedidoItem pedidoItem = new PedidoItem();
                pedidoItem.setPedidoId(pedidoId);
                pedidoItem.setTenisId(item.getTenis().getId());
                pedidoItem.setQuantidade(item.getQuantidade());
                pedidoItem.setPrecoUnitario(item.getTenis().getPreco());
                pedidoItems.add(pedidoItem);
            }

            // Adiciona os itens ao pedido
            PedidoItemDao pedidoItemDao = new PedidoItemDao();
            pedidoItemDao.adicionarItensAoPedido(pedidoItems);

            // Limpa o carrinho após a compra
            carrinhoDao.limparCarrinho(clienteId);

            // Redireciona para a página de sucesso (exibe o resumo do pedido com a forma de pagamento)
            resp.sendRedirect("/area-cliente");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao finalizar a compra.");
        }
    }
}
