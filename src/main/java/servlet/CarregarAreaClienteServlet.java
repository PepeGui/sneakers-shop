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

@WebServlet("/area-cliente")
public class CarregarAreaClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pedido> pedidos = new ArrayList<>();
        PedidoDao pedidoDao = new PedidoDao();
        HttpSession session = req.getSession();
        Integer clienteId = (Integer) session.getAttribute("clienteId");

        try {
            pedidos.addAll(pedidoDao.obterPedidosPorCliente(clienteId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("pedidos", pedidos);

        req.getRequestDispatcher("Area-Cliente/AreaCliente.jsp").forward(req,resp);
    }
}