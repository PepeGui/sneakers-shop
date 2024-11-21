package servlet;

import dao.ClienteDao;
import model.Cliente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login-cliente")
public class LoginClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Obtém os parâmetros de login
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            ClienteDao clienteDao = new ClienteDao();

            // Verifica se as credenciais são válidas
            if (clienteDao.verificarLogin(new Cliente(email, senha))) {
                Cliente cliente = clienteDao.buscarClientePorEmail(email);

                // Armazena o cliente na sessão
                HttpSession session = req.getSession();
                session.setAttribute("clienteId", cliente.getId());
                session.setAttribute("clienteNome", cliente.getNome());

                // Redireciona para a página principal
                resp.sendRedirect("/index.jsp");
            } else {
                // Login falhou, redireciona para a página de login com mensagem de erro
                req.setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
                req.getRequestDispatcher("/login-cliente.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor. Tente novamente mais tarde.");
        }
    }
}
