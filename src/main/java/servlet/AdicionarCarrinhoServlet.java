package servlet;

import dao.CarrinhoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/adicionarCarrinho")
public class AdicionarCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtém o ID do tênis e a quantidade
            int tenisId = Integer.parseInt(request.getParameter("id"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            // Obtém o clienteId da sessão
            HttpSession session = request.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            if (clienteId == null) {
                response.sendRedirect("/Cadastro-Cliente/cadastro-cliente.jsp");
                return;
            }


            // Instancia o DAO e adiciona o item ao carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            boolean sucesso = carrinhoDao.adicionarItemAoCarrinho(clienteId, tenisId, quantidade);

            // Redireciona ou exibe erro com base no sucesso
            if (sucesso) {
                response.sendRedirect("/Carrinho/carrinho.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao adicionar item ao carrinho.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }
}

