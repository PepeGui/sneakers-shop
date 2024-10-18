package servlet;

import model.Cliente;
import model.Endereco;
import dao.ClienteDao;
import controller.Criptografia; // Importando a classe de criptografia
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cadastro-cliente")
public class CadastroClienteServlet extends HttpServlet {// Substitua pela sua chave em Base64
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            String dataNascimento = request.getParameter("dataNascimento");
            String genero = request.getParameter("genero");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            // Gera uma nova chave AES (Apenas para fins de exemplo; idealmente, você deve reutilizar uma chave).
            SecretKey chaveAES = Criptografia.gerarChaveAES(128);
            String chaveAESCodificada = Criptografia.converterChaveParaString(chaveAES);

            // Criptografar a senha usando a chave gerada.
            String senhaCriptografada = Criptografia.criptografar(senha, chaveAES);

            // Criando o objeto Cliente.
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setDataNascimento(dataNascimento);
            cliente.setGenero(genero);
            cliente.setCpf(cpf);
            cliente.setEmail(email);
            cliente.setSenha(senhaCriptografada);
            cliente.setChaveAES(chaveAESCodificada); // Armazene a chave associada para futuras operações de descriptografia.

            // Captura dos endereços.
            // (Aqui você deve continuar com a lógica de capturar endereços)

            // Salva no banco.
            ClienteDao clienteDao = new ClienteDao();
            if (clienteDao.cadastrarCliente(cliente)) {
                response.sendRedirect("login.jsp");
            } else {
                response.sendRedirect("cadastro-cliente.jsp?erro=Erro ao cadastrar cliente.");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao processar o cadastro", e);
        }
    }

}
