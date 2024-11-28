package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/salvar-endereco-sessao")
public class SalvarEnderecoNaSessaoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException , IOException{

        try{
            int enderecoId = Integer.parseInt(req.getParameter("enderecoId"));

            HttpSession session = req.getSession();
            session.setAttribute("enderecoId", enderecoId);

            System.out.println("endereco id " + enderecoId);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/Escolher-Pagamento/escolher-pagamento.jsp");
            dispatcher.forward(req,resp);
        } catch (Exception err) {
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao salvar endereco na sessão");
        }
    }
}
