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
import java.util.*;

@WebServlet("/find-all-pedidos")
public class ListarPedidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Pedido> pedidos = new ArrayList<>();
        PedidoDao pedidoDao = new PedidoDao();

        String pesquisa = req.getParameter("pesquisa");


        try {
            if(pesquisa == null || pesquisa.isBlank() )
                pedidos.addAll(pedidoDao.getAllPedidos());
            else
                pedidos.add(pedidoDao.buscaPedidoPorID(Integer.parseInt(pesquisa)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("pedidos", pedidos);

        req.getRequestDispatcher("/Listar_Pedido/listar_pedido.jsp").forward(req,resp);
    }
}