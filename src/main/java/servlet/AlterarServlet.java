package servlet;

import dao.UsuarioDao;
import model.Usuario;
import service.UsuarioService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tela-alterar")
public class AlterarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = new Usuario();
        try {
            System.out.println("passou aqui");
            String id = request.getParameter("id");

            usuario.setId(Integer.parseInt(id));
            UsuarioDao usuarioDao = new UsuarioDao();
            usuario = usuarioDao.buscaUsuariosPorID(usuario);
            System.out.println("passou aqui2");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de ID inválido. Por favor, forneça um ID numérico.");
        }
        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher("/Alterar/alterar.jsp").forward(request,response);

    }
}