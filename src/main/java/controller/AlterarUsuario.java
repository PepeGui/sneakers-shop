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

            Usuario lUser = new Usuario();
            lUser = usuarioDao.buscaUsuariosPorID(pUser);
            String senhaDescrip = crip.descriptografar(lUser.getSenha(),crip.converterStringParaChave(lUser.getChaveAES(),"AES"));

            if(pUser.getSenha() != senhaDescrip){
                // Gerando uma chave AES de 128 bits
                SecretKey chaveAES = crip.gerarChaveAES(128);
                // Criptografando a senha
                String senhaCriptografada = crip.criptografar(pUser.getSenha(), chaveAES);
                pUser.setSenha(senhaCriptografada);
                pUser.setChaveAES(crip.converterChaveParaString(chaveAES));
            }

            if(cad.isCPFValido(String.valueOf(pUser.getCpf()))) {
                return usuarioDao.alterarUsuario(pUser);
            }
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
