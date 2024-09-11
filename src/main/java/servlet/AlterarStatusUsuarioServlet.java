package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDao;
import model.Usuario;

@WebServlet("/alterarStatus")
public class AlterarStatusUsuarioServlet extends HttpServlet {

    UsuarioDao usuarioDao = new UsuarioDao();
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String id = request.getParameter("id");
        String novoStatus = request.getParameter("status");

        Usuario u = new Usuario(Integer.parseInt(id));
        
        if (id == null || novoStatus == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ou status ausente");
            return;
        }
        
        try {
            usuarioDao.alterarStatus(u);
            response.sendRedirect("listaUsuarios.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o status");
        }
    }

    
}
