package servlet;

import dao.CarrinhoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/removerCarrinho")
public class RemoverCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer clienteId = null;  // Declarando clienteId fora do try-catch para usar no log

        try {
            // Obtém o ID do tênis do parâmetro da requisição
            int tenisId;
            try {
                tenisId = Integer.parseInt(request.getParameter("tenisId"));
                System.out.println("Tenis ID: " + tenisId);  // Log do tenisId recebido
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do tênis inválido.");
                return;
            }

            // Obtém o clienteId da sessão
            HttpSession session = request.getSession();
            clienteId = (Integer) session.getAttribute("clienteId");
            System.out.println("Cliente ID: " + clienteId);  // Log do clienteId obtido da sessão

            // Verifica se o clienteId está presente
            if (clienteId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Você precisa estar logado.");
                return;
            }

            // Instancia o CarrinhoDao e remove o item do carrinho para o cliente específico
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            boolean sucesso = carrinhoDao.removerItemDoCarrinho(clienteId, tenisId);

            // Redireciona ou exibe erro com base no sucesso da operação
            if (sucesso) {
                response.sendRedirect("/carrinho"); // Redireciona para a página do carrinho
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao remover item do carrinho.");
            }

        } catch (Exception e) {
            // Log para qualquer outra exceção não tratada
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }
}
