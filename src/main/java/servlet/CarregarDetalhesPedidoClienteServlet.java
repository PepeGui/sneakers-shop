package servlet;

import dao.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/detalhes-pedido")
public class CarregarDetalhesPedidoClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pedidoIdParam = req.getParameter("pedidoId");
        if (pedidoIdParam == null || pedidoIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do pedido não fornecido.");
            return;
        }

        int pedidoId;
        try {
            pedidoId = Integer.parseInt(pedidoIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do pedido inválido.");
            return;
        }

        Pedido pedido;
        Endereco endereco;
        List<PedidoItem> itensPedidos = new ArrayList<>();
        TenisDao tenisDao = new TenisDao();

        try {
            // Busca informações do pedido
            PedidoDao pedidoDao = new PedidoDao();
            pedido = pedidoDao.buscaPedidoPorID(pedidoId);

            // Verifica se o pedido existe
            if (pedido == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Pedido não encontrado.");
                return;
            }

            // Busca endereço de entrega
            EnderecoDao enderecoDao = new EnderecoDao();
            endereco = enderecoDao.buscaEnderecoPorID(pedido.getEnderecoEntregaId());

            // Busca os itens do pedido
            PedidoItemDao pedidoItemDao = new PedidoItemDao();
            itensPedidos = pedidoItemDao.obterItensPorPedidoComTenis(pedidoId);

            // Associa os detalhes do tênis a cada item do pedido
            for (PedidoItem item : itensPedidos) {
                Tenis tenis = tenisDao.getTenisById(item.getTenisId());
                item.setTenis(tenis);
            }

        } catch (SQLException e) {
            throw new ServletException("Erro ao carregar os detalhes do pedido: " + e.getMessage(), e);
        }

        // Define os atributos para a JSP
        req.setAttribute("pedido", pedido);
        req.setAttribute("enderecoEntrega", endereco);
        req.setAttribute("itensPedido", itensPedidos);

        // Encaminha para a JSP
        req.getRequestDispatcher("Detalhes-Pedido/detalhes-pedido.jsp").forward(req, resp);
    }
}
