package servlet;

import controller.Criptografia;
import model.Usuario;
import service.UsuarioService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;

@WebServlet("/login-usuario")
public class LoginUsuarioServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServerException, IOException {

        try {
            String email = req.getParameter("email");
            String senha = req.getParameter("senha");

            // Gera uma chave AES de 16 bytes (128 bits)
            //SecretKey chaveAES = Criptografia.gerarChaveAES(128);

            // Criptografa a senha usando AES
            //String senhaCriptografada = Criptografia.criptografar(senha, chaveAES);

            System.out.println(email);
            //System.out.println(senhaCriptografada);  // Exibe a senha criptografada
            System.out.println(senha);  // Exibe a senha

            Usuario usuario = new Usuario(email, senha);

            UsuarioService usuarioService = new UsuarioService();

            if (usuarioService.verificarLogin(usuario)) {
                resp.sendRedirect("Principal/principal.jsp");
            } else {
                req.setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
                req.getRequestDispatcher("Login/login.jsp").forward(req, resp);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

