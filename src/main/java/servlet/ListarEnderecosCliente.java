package servlet;


import dao.EnderecoDao;
import model.Endereco;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/find-all-enderecos-cliente")
public class ListarEnderecosCliente extends HttpServlet {

    private EnderecoDao enderecoDao = new EnderecoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Recupera o id do clinete da sessão
        HttpSession session = request.getSession();
        Integer clienteId = (Integer) session.getAttribute("clienteId");

        if (clienteId == null){
            response.sendRedirect("/index.jsp");
        }

        try{
            //Busca endereços do cliente pelo id
            List<Endereco> enderecos = enderecoDao.buscarEnderecosPorClienteId(clienteId);
            request.setAttribute("enderecos", enderecos);
        } catch(SQLException err) {
            err.printStackTrace();
            request.setAttribute("erro", "Erro ao buscar endereços");
        }

        //Encaminhar para a página JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Escolher-Endereco-Entrega/escolher-endereco-entrega.jsp");
        dispatcher.forward(request,response);

    }

}
