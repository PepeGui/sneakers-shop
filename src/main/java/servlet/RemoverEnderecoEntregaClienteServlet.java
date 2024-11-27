package servlet;


import dao.EnderecoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/removerEnderecoEntrega")
public class RemoverEnderecoEntregaClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        try{
            int enderecoId = Integer.parseInt(req.getParameter("enderecoId"));

            EnderecoDao enderecoDao = new EnderecoDao();
            boolean sucesso = enderecoDao.deleteEnderecoById(enderecoId);

            if (sucesso){
                resp.sendRedirect("/find-all-enderecos-cliente");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao remover o endereco");
            }
        } catch (NumberFormatException err){
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"Parâmetro inválidao");
        } catch (Exception err) {
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Erro ao processar a requisição");
        }
    }

}
