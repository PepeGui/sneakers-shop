package service;

import dao.UsuarioDao;
import model.Usuario;

public class UsuarioService {

    private UsuarioDao usuarioDao = new UsuarioDao();

    // Modifica o método verificarLogin para retornar um objeto Usuario
    public Usuario verificarLogin(String email, String senha) {
        // Busca o usuário pelo email
        Usuario usuario = usuarioDao.buscarUsuarioPorEmail(email);

        // Se o usuário for encontrado e a senha for válida
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return usuario; // Retorna o usuário, incluindo o grupo
        }
        return null; // Se não encontrar ou a senha estiver errada, retorna null
    }

    public boolean verificarLogin(Usuario u) {
        // Se for usar esse método, precisa verificar login aqui.
        return u != null;
    }
}
