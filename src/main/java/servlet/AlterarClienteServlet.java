package servlet;

import controller.Criptografia;
import dao.*;
import model.*;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/alterar-cliente")
public class AlterarClienteServlet extends HttpServlet {// Substitua pela sua chave em Base64
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ClienteDao clienteDao = new ClienteDao();
            String nome = request.getParameter("nome");
            String dataNascimento = request.getParameter("dataNascimento");
            String genero = request.getParameter("genero");
            String cpf = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            HttpSession session = request.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            Cliente cliente = new Cliente();
            cliente.setId(clienteId);
            cliente = clienteDao.buscaClientesPorID(cliente);


            // Criando o objeto Cliente.
            cliente.setNome(nome);
            cliente.setDataNascimento(dataNascimento);
            cliente.setGenero(genero);
            cliente.setCpf(cpf);
            cliente.setEmail(email);

            String senhaDescrip = Criptografia.descriptografar(cliente.getSenha(),Criptografia.converterStringParaChave(cliente.getChaveAES(),"AES"));

            if(!senha.equals(senhaDescrip)){
                SecretKey chaveAES = Criptografia.gerarChaveAES(128);
                String chaveAESCodificada = Criptografia.converterChaveParaString(chaveAES);

                String senhaCriptografada = Criptografia.criptografar(senha, chaveAES);

                cliente.setSenha(senhaCriptografada);
                cliente.setChaveAES(chaveAESCodificada);
            }



            // Salva no banco.
            if (clienteDao.alterarCliente(cliente)) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect("cadastro-cliente.jsp?erro=Erro ao cadastrar cliente.");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao processar o cadastro", e);
        }
    }

}
