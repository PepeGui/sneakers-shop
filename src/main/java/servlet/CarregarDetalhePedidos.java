package servlet;


import dao.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/detalhes-pedido")
public class CarregarDetalhePedidos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PedidoItem> itensPedidos = new ArrayList<>();
        String pedidoId = req.getParameter("pedidoId");
        Pedido pedido = new Pedido();
        Endereco endereco = new Endereco();

        try {
            itensPedidos.addAll(new PedidoItemDao().obterItensPorPedido(Integer.parseInt(pedidoId)));
            pedido = new PedidoDao().buscaPedidoPorID(Integer.parseInt(pedidoId));
            endereco = new EnderecoDao().buscaEnderecoPorID(pedido.getEnderecoEntregaId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("itensPedido", itensPedidos);
        req.setAttribute("pedido", pedido);
        req.setAttribute("enderecoEntrega", endereco);

        req.getRequestDispatcher("Detalhes-Pedido/detalhes-pedido.jsp").forward(req,resp);
    }
}