package controller;

import dao.UsuarioDao;
import model.Usuario;

public class CadastrarUsuario {
    public Boolean CadastrarUser(Usuario pUser){
        UsuarioDao usuarioDao = new UsuarioDao();
        if(usuarioDao.VerificacaoEmail(pUser)) {
            usuarioDao.createUsuario(pUser);
            return true;
        }
        else
            return false;
    }
}
