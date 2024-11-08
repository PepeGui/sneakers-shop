package servlet;

import dao.CarrinhoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/removerCarrinho")
public class RemoverCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtém o ID do tênis do parâmetro da requisição
            int tenisId = Integer.parseInt(request.getParameter("tenisId"));

            // Instancia o CarrinhoDao e remove o item do carrinho
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            boolean sucesso = carrinhoDao.removerItemDoCarrinho(tenisId);

            // Redireciona ou exibe erro com base no sucesso da operação
            if (sucesso) {
                response.sendRedirect("/Carrinho/carrinho.jsp"); // Volta para a página do carrinho
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao remover item do carrinho.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar a requisição.");
        }
    }
}
