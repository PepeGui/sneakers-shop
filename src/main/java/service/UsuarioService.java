package service;

import dao.UsuarioDao;
import model.Usuario;


import java.util.List;


public class UsuarioService {

    private UsuarioDao usuarioDao = new UsuarioDao();

    public boolean verificarLogin(Usuario u) {
        return usuarioDao.verificarLogin(u); // Delegando a verificação para o DAO
    }
}

