package servlet;

import model.Usuario;
import service.UsuarioService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login-usuario")
public class LoginUsuarioServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            Usuario usuario = new Usuario(email, senha);
            UsuarioService usuarioService = new UsuarioService();

            // Verifica o login
            if (usuarioService.verificarLogin(usuario)) {
                // Cria ou obtém a sessão do usuário
                HttpSession session = req.getSession();
                session.setAttribute("usuarioLogado", usuario);  // Armazena o objeto Usuario na sessão

                // Redireciona para a página principal
                resp.sendRedirect("Principal/principal.jsp");
            } else {
                // Login falhou, redireciona para a página de login com uma mensagem de erro
                req.setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
                req.getRequestDispatcher("Login/login.jsp").forward(req, resp);
            }
        } catch (Exception err) {
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor. Tente novamente mais tarde.");
        }
    }
}
