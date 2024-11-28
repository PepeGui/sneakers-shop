package servlet;

import dao.TenisDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AlterarEstoque")
public class AlterarEstoqueServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera o ID do tênis e a quantidade de estoque do formulário
        String id = request.getParameter("id");
        String estoqueStr = request.getParameter("estoque");

        // Validação simples de entrada
        if (id != null && !id.isEmpty() && estoqueStr != null && !estoqueStr.isEmpty()) {
            try {
                int idTenis = Integer.parseInt(id);
                int estoque = Integer.parseInt(estoqueStr);

                // Chama o método de atualização no DAO
                TenisDao tenisDao = new TenisDao();
                boolean sucesso = tenisDao.atualizarEstoqueTenis(estoque, idTenis);

                if (sucesso) {
                    // Se a atualização for bem-sucedida, redireciona para a página de sucesso ou lista de tênis
                    response.sendRedirect(request.getContextPath() + "/find-all-tenis");
                } else {
                    // Caso haja erro na atualização, exibe mensagem de erro
                    request.setAttribute("error", "Erro ao atualizar o estoque.");
                    request.getRequestDispatcher("/AlterarEstoque/alterar-estoque.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                // Se ocorrer erro ao converter o ID ou estoque para inteiros
                request.setAttribute("error", "Dados inválidos. Por favor, insira valores numéricos.");
                request.getRequestDispatcher("/AlterarEstoque/alterar-estoque.jsp").forward(request, response);
            }
        } else {
            // Se o ID ou o estoque não foram fornecidos
            request.setAttribute("error", "ID ou estoque não fornecidos.");
            request.getRequestDispatcher("/AlterarEstoque/alterar-estoque.jsp").forward(request, response);
        }
    }
}
