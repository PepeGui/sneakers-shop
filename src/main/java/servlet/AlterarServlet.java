package servlet;

import controller.Criptografia;
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
        Criptografia crip = new Criptografia();
        try {
            System.out.println("passou aqui");
            String id = request.getParameter("id");

            usuario.setId(Integer.parseInt(id));
            UsuarioDao usuarioDao = new UsuarioDao();
            usuario = usuarioDao.buscaUsuariosPorID(usuario);
            String senhaDescrip = crip.descriptografar(usuario.getSenha(),crip.converterStringParaChave(usuario.getChaveAES(),"AES"));

            usuario.setSenha(senhaDescrip);
            System.out.println("passou aqui2");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de ID inválido. Por favor, forneça um ID numérico.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("usuario", usuario);

        request.getRequestDispatcher("/Alterar/alterar.jsp").forward(request,response);

    }
}