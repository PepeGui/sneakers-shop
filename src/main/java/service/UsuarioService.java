package service;

import dao.UsuarioDao;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    public boolean verificarLogin(Usuario u) {
        List<Usuario> usuarios = usuarioDao.getAllUsuarios();

        for (Usuario us : usuarios) {
            if (us.getEmail().equals(u.getEmail()) && us.getSenha().equals(u.getSenha())) {
                System.out.println("Sucesso no login");
                return true;
            }
        }

        System.out.println("Falha no login, verifique se o email ou senha são inválidos");
        return false;
    }
}

