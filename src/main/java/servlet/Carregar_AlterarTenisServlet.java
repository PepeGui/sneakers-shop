package servlet;

import dao.TenisDao;
import model.Tenis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/tela-alterarTenis")
public class Carregar_AlterarTenisServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tenis tenis = new Tenis();
        HttpSession session = request.getSession();
        String usuarioGrupo = (String) session.getAttribute("usuarioGrupo");

        try {
            // Recuperar o parâmetro de ID da requisição
            String id = request.getParameter("id");

            // Verificar se o ID é válido
            if (id != null && !id.isEmpty()) {
                tenis.setId(Integer.parseInt(id));

                TenisDao tenisDao = new TenisDao();

                // Buscar o tênis pelo ID
                tenis = tenisDao.buscaTenisPorID(tenis);

                // Verificar se o tênis foi encontrado
                if (tenis == null) {
                    request.setAttribute("error", "Tênis não encontrado.");
                }
            } else {
                request.setAttribute("error", "ID do tênis não fornecido.");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de ID inválido. Por favor, forneça um ID numérico.");
        }

        // Verificar o grupo do usuário e redirecionar conforme o caso
        if ("Admin".equals(usuarioGrupo)) {
            // Se o grupo for Admin, continua normalmente e encaminha para a página de alteração
            request.setAttribute("tenis", tenis);
            request.setAttribute("grupo", usuarioGrupo);
            request.getRequestDispatcher("/AlterarTenis/alterartenis.jsp").forward(request, response);
        } else if ("Estoquista".equals(usuarioGrupo)) {
            // Se o grupo for Estoquista, mantém os dados e encaminha para a página de Alterar Estoque
            request.setAttribute("tenis", tenis);
            request.getRequestDispatcher("/AlterarEstoque/alterar-estoque.jsp").forward(request, response);
        } else {
            // Caso o grupo não seja reconhecido, pode redirecionar para uma página de erro ou login
            response.sendRedirect("/login");
        }
    }
}

