<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <link rel="stylesheet" href="/Cadastro-Usuario/Cadastro.css">
</head>
<body>
<div class="cadastro-container">
    <h2>Cadastro</h2>
    <form action="/alterar-usuario" method="POST">
        <div class="input-group">
            <label for="nome">Nome completo</label>
            <input type="text" id="nome" name="nome" value = ${usuario.nome}  required>
        </div>
        <div class="input-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value = ${usuario.email} required>
        </div>
        <div class="input-group">
            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" value = ${usuario.senha} required>
        </div>
        <div class="input-group">
                    <label for="confirmar-password">Confirmar Senha</label>
                    <input type="password" id="confirmar-password" name="confirmar-password" value = ${usuario.senha} required>
        </div>
        <div class="input-group">
            <label for="cpf">CPF</label>
            <input type="text" id="cpf" name="cpf" value = ${usuario.cpf} required>
        </div>
        <div class="input-group">
            <label for="grupo">Grupo</label>
            <input type="text" id="grupo" name="grupo" value=${usuario.grupo} required>
        </div>
        <input type="hidden" name="id" value = ${usuario.id}">
        <button type="submit">Finalizar</button>
    </form>
</div>
</body>
</html>
