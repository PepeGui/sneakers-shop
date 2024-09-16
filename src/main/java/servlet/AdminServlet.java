package servlet;

import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/adminFuncionalidade")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

        if (usuario != null && "Administrador".equals(usuario.getGrupo())) {
            // Executa funcionalidades de administrador
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
        }
    }
}

