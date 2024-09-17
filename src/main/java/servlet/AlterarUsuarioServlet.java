package servlet;

import controller.AlterarUsuario;
import controller.CadastrarUsuario;
import dao.UsuarioDao;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/alterar-usuario")
public class AlterarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDao usuarioDao = new UsuarioDao();

        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmar-password");
        String cpf = request.getParameter("cpf");
        String grupo = request.getParameter("grupo");

        if(senha.equals(confirmarSenha)) {
            Usuario user = new Usuario(Integer.parseInt(id), nome, email, senha, Long.parseLong(cpf), grupo, true);
            AlterarUsuario alterarUsuario = new AlterarUsuario();
            boolean resultadoAlteracao = alterarUsuario.AlterarUsuario(user);
            if(resultadoAlteracao == true) {
                System.out.println("passou aqui if ");
                response.sendRedirect("/find-all-usuarios");
            }
            else
            {
                System.out.println("passou aqui if else");
                request.setAttribute("id", id);
                request.getRequestDispatcher("/tela-alterar").forward(request, response);

            }
        }
        else {
            System.out.println("passou aqui else");
            request.setAttribute("id", id);
            request.getRequestDispatcher("/tela-alterar").forward(request, response);
        }
    }
}