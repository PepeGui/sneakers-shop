<!DOCTYPE html>
<html lang="pt-BR">
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela de Login</title>
    <link rel="stylesheet" href="/Login/login.css">
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <form action="/login-usuario" method="GET">
        <div class="input-group">
            <label for="email">Usuário</label>
            <input type="text" id="email" name="email" required>
        </div>
        <div class="input-group">
            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" required>
        </div>
        <!-- O botão está agora dentro do formulário e não possui link -->
        <button type="submit">Entrar</button>
    </form>
</div>
</body>
</html>
