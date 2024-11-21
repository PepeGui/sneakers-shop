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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/confirmar-compra")
public class ConfirmarCompraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            if (clienteId == null) {
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            // Passa o clienteId para obter os itens do carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);  // Alteração aqui

            double subtotal = 0.0;
            for (ItemCarrinho item : itensCarrinho) {
                subtotal += item.getTenis().getPreco() * item.getQuantidade();
            }
            double total = subtotal + 30.0;

            req.setAttribute("itensCarrinho", itensCarrinho);
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("total", total);

            // Buscar os endereços do cliente
            EnderecoDao enderecoDao = new EnderecoDao();
            List<Endereco> enderecos = enderecoDao.buscarEnderecosPorClienteId(clienteId);
            req.setAttribute("enderecos", enderecos);

            req.getRequestDispatcher("/finalizar-compra.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar os dados do carrinho.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            if (clienteId == null) {
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            int enderecoId = Integer.parseInt(req.getParameter("enderecoId"));

            // Passa o clienteId para obter os itens do carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);  // Alteração aqui

            double subtotal = 0.0;
            List<PedidoItem> pedidoItems = new ArrayList<>();
            for (ItemCarrinho item : itensCarrinho) {
                subtotal += item.getTenis().getPreco() * item.getQuantidade();
                pedidoItems.add(new PedidoItem(0, item.getTenis().getId(), item.getQuantidade(), item.getTenis().getPreco()));
            }

            double frete = 30.0;
            double total = subtotal + frete;

            Pedido pedido = new Pedido(clienteId, enderecoId, total, "esperando o pagamento", pedidoItems);
            PedidoDao pedidoDao = new PedidoDao();
            int pedidoId = pedidoDao.criarPedido(pedido);

            if (pedidoId > 0) {
                for (PedidoItem item : pedidoItems) {
                    item.setPedidoId(pedidoId);
                }

                PedidoItemDao pedidoItemDao = new PedidoItemDao();
                pedidoItemDao.adicionarItensAoPedido(pedidoItems);

                session.setAttribute("pedidoId", pedidoId);
                session.setAttribute("total", total);

                resp.sendRedirect("/Escolher-Pagamento/escolher-pagamento.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar o pedido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a compra.");
        }
    }
}
