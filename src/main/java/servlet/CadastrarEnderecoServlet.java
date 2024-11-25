package servlet;

import dao.EnderecoDao;
import dao.ClienteDao;
import model.Endereco;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cadastrar-endereco")
public class CadastrarEnderecoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtém os dados do formulário
            String cep = req.getParameter("cep");
            String logradouro = req.getParameter("logradouro");
            String numero = req.getParameter("numero");
            String bairro = req.getParameter("bairro");
            String cidade = req.getParameter("cidade");
            String uf = req.getParameter("uf");

            // Cria o objeto endereço
            Endereco endereco = new Endereco(cep, logradouro, numero, bairro, cidade, uf);

            // Obtém o cliente da sessão
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            if (clienteId == null) {
                resp.sendRedirect("/login-cliente.jsp"); // Se o cliente não estiver logado, redireciona para login
                return;
            }

            // Salva o endereço no banco e associa ao cliente
            EnderecoDao enderecoDao = new EnderecoDao();
            int enderecoId = enderecoDao.salvarEndereco(endereco); // Salva o endereço no banco

            ClienteDao clienteDao = new ClienteDao();
            clienteDao.associarEnderecoComCliente(clienteId, enderecoId); // Associa o endereço ao cliente

            // Redireciona para a página de sucesso
            resp.sendRedirect("/find-all-enderecos-cliente");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar o endereço.");
        }
    }
}
