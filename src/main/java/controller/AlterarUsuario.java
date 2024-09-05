package controller;

import dao.UsuarioDao;
import model.Usuario;

import javax.crypto.SecretKey;

public class AlterarUsuario {
    Criptografia crip = new Criptografia();
    CadastrarUsuario cad = new CadastrarUsuario();
    public Boolean AlterarUsuario(Usuario pUser){
        try {
            UsuarioDao usuarioDao = new UsuarioDao();

            // Gerando uma chave AES de 128 bits
            SecretKey chaveAES = crip.gerarChaveAES(128);
            // Criptografando a senha
            String senhaCriptografada = crip.criptografar(pUser.getSenha(), chaveAES);
            pUser.setSenha(senhaCriptografada);

            if(usuarioDao.VerificacaoEmail(pUser) && cad.isCPFValido(String.valueOf(pUser.getCpf()))) {
                usuarioDao.alterarUsuario(pUser);
                return true;
            }
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
