package controller;

import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UsuarioService;

@RestController
@RequestMapping("/api/login")
public class LoginUsuario {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean isLoginSuccessful = usuarioService.verificarLogin(usuario);
        if (isLoginSuccessful) {
            return ResponseEntity.ok("Login realizado com sucesso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no login, email ou senha inválidos");
        }
    }
}

