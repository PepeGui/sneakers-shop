package servlet;

import controller.Criptografia;
import dao.*;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/tela-alterarPedido")
public class CarregarAlterarClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cliente cliente = new Cliente();
        Criptografia crip = new Criptografia();
        try {
            HttpSession session = request.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            cliente.setId(clienteId);
            ClienteDao clienteDao = new ClienteDao();
            cliente = clienteDao.buscaClientesPorID(cliente);
            //cliente.setEnderecosEntrega();
            String senhaDescrip = crip.descriptografar(cliente.getSenha(),crip.converterStringParaChave(cliente.getChaveAES(),"AES"));

            cliente.setSenha(senhaDescrip);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de ID inválido. Por favor, forneça um ID numérico.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("cliente", cliente);

        request.getRequestDispatcher("Alterar-Cliente/alterar-cliente.jsp").forward(request,response);

    }
}