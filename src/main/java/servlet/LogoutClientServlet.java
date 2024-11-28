package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout-cliente")
public class LogoutClientServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtém a sessão atual (se existir)
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Invalida a sessão para realizar o logout
            session.invalidate();
        }
        // Redireciona para a página principal ou de login
        response.sendRedirect("/index.jsp");
    }
}
