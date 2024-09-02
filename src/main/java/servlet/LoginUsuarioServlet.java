package servlet;

import model.Usuario;
import service.UsuarioService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet("/login-usuario")
public class LoginUsuarioServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {

        try{
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            System.out.println(email);
            System.out.println(senha);

            Usuario usuario = new Usuario(email,senha);

            UsuarioService usuarioService = new UsuarioService();

            if (usuarioService.verificarLogin(usuario)){
                resp.sendRedirect("Principal/principal.html");
            }else {
                req.setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
                req.getRequestDispatcher("login.html").forward(req, resp);
            }

        }catch (Exception err){
            err.getMessage();
        }

    }
}
