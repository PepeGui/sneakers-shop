package servlet;


import model.Usuario;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet("/login-usuario")
public class LoginUsuarioServler extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        System.out.println(email);
        System.out.println(senha);

        Usuario usuario = new Usuario();

    }
}
