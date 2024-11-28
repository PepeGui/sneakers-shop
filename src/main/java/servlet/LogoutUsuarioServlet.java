package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout-usuario")
public class LogoutUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Obtém a sessão atual sem criar uma nova
        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }
        // Redireciona para a página de login
        response.sendRedirect("/Login/login.jsp");
    }
}
