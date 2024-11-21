package servlet;


import dao.ClienteDao;
import dao.UsuarioDao;
import model.Cliente;
import model.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login-cliente")
public class LoginClienteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            Cliente cliente = new Cliente(email, senha);
            ClienteDao clienteDao = new ClienteDao();

            // Verifica o login
            if (clienteDao.verificarLogin(cliente)) {
                cliente = clienteDao.buscarClientePorEmail(email);
                req.setAttribute("cliente", cliente);  // Armazena o objeto Usuario na sessão

                // Redireciona para a página principal
                req.getRequestDispatcher("/").forward(req,resp);
            } else {
                // Login falhou, redireciona para a página de login com uma mensagem de erro
                req.setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
            }
        } catch (Exception err) {
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor. Tente novamente mais tarde.");
        }
    }
}
