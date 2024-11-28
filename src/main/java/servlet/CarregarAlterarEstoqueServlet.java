package servlet;

import dao.TenisDao;
import model.Tenis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CarregarAlterarEstoque")
public class CarregarAlterarEstoqueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera o ID do tênis da requisição
        String id = request.getParameter("id");

        if (id != null && !id.isEmpty()) {
            TenisDao tenisDao = new TenisDao();
            Tenis tenis = new Tenis();
            tenis.setId(Integer.parseInt(id));

            // Busca o tênis no banco de dados
            tenis = tenisDao.buscaTenisPorID(tenis);

            if (tenis != null) {
                // Se o tênis foi encontrado, coloca o objeto no request
                request.setAttribute("tenis", tenis);
                // Encaminha para a tela de alteração de estoque
                request.getRequestDispatcher("/AlterarEstoque/alterar-estoque.jsp").forward(request, response);
            } else {
                // Se o tênis não foi encontrado, exibe erro
                request.setAttribute("error", "Tênis não encontrado.");
                request.getRequestDispatcher("/erro.jsp").forward(request, response);
            }
        } else {
            // Se o ID não for válido ou estiver ausente
            request.setAttribute("error", "ID do tênis não fornecido.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }
}
