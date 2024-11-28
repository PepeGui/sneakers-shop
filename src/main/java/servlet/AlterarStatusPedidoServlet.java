package servlet;

import dao.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/alterarStatusPedido")
public class AlterarStatusPedidoServlet extends HttpServlet {

    PedidoDao pedidoDao = new PedidoDao();
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String status = request.getParameter("status");


        if (id == null || status == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ou status ausente");
            return;
        }

        switch (status) {
            case "Pendente":
                status = "Pedido Aceito";
                break;
            case "Pedido Aceito":
                status = "Pedido em processamento";
                break;
            case "Pedido em processamento":
                 status = "Pedido saiu para entrega";
                break;
            case "Pedido saiu para entrega":
                 status = "Pedido concluido";
                break;
            default:
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/find-all-pedidos");
        }
        Pedido pedido = new Pedido();
        pedido.setId(Integer.parseInt(id));
        pedido.setStatus(status);  // Define o status baseado no parâmetro recebido

        try {
            boolean sucesso = pedidoDao.alterarStatus(pedido);
            if (sucesso) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.sendRedirect("/find-all-pedidos");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o status");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar o ID");
        }
    }
}
