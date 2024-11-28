package servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/carregar-menu-usuario")
public class CarregarMenuUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Obtém a sessão do usuário
            HttpSession session = request.getSession(false); // `false` para evitar criar uma nova sessão

            // Verifica se a sessão existe e contém o atributo `usuarioId`
            if (session != null && session.getAttribute("usuarioId") != null) {
                // Pega o ID do usuário e outros atributos necessários
                Integer usuarioId = (Integer) session.getAttribute("usuarioId");
                String usuarioNome = (String) session.getAttribute("usuarioNome");
                String usuarioGrupo = (String) session.getAttribute("usuarioGrupo");

                // Log para depuração
                System.out.println("Usuário logado: ID=" + usuarioId + ", Nome=" + usuarioNome + ", Grupo=" + usuarioGrupo);

                // Define os atributos para a página JSP, caso necessário
                request.setAttribute("usuarioId", usuarioId);
                request.setAttribute("usuarioNome", usuarioNome);
                request.setAttribute("usuarioGrupo", usuarioGrupo);

                // Encaminha para a página principal
                request.getRequestDispatcher("/Principal/principal.jsp").forward(request, response);
            } else {
                // Se o usuário não estiver logado, redireciona para a página de login
                response.sendRedirect("/Login/login.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao carregar o menu do usuário.");
        }
    }
}
