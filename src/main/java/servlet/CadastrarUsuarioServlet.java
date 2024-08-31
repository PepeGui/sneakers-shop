package servlet;

import controller.CadastrarUsuario;
import dao.UsuarioDao;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/criar-usuario")
public class CadastrarUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioDao usuarioDao = new UsuarioDao();

        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");
        String cpf = request.getParameter("cpf");
        String grupo = request.getParameter("grupo");

        if(senha.equals(confirmarSenha)) {
            Usuario user = new Usuario(nome, email, senha, Long.parseLong(cpf), grupo, true);
            boolean resultadoCadastrado = new CadastrarUsuario().CadastrarUser(user);
        }
        //request.setAttribute("pedido", pedido);

        request.getRequestDispatcher("/").forward(request, response);
    }
}