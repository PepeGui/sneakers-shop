<!doctype html>
<html lang="pt-BR">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Cliente, dao.ClienteDao" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Cliente - Sneaker-Shop</title>
    <link rel="stylesheet" href="Alterar-Cliente/alterar-cliente.css">
</head>
<body>
    <h2>Alteração de Dados do Cliente</h2>
    <form action="/alterar-cliente" method="POST">

        <!-- Campo para Nome Completo -->
        <label for="nome">Nome Completo:</label>
        <input type="text" id="nome" name="nome" value="${cliente.nome}" required pattern="[A-Za-z]{3,} [A-Za-z]{3,}"
               title="O nome deve conter pelo menos duas palavras com 3 letras cada.">

        <!-- Campo para Data de Nascimento -->
        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" id="dataNascimento" name="dataNascimento" value="${cliente.dataNascimento}" required>

        <!-- Campo para Gênero -->
        <label for="genero">Gênero:</label>
        <select id="genero" name="genero" required>
            <option value="masculino"  ${cliente.genero} == "masculino" ? "selected" : "" >Masculino</option>
            <option value="feminino" ${cliente.genero} == "feminino" ? "selected" : "" >Feminino</option>
            <option value="outro" ${cliente.genero} =="outro" ? "selected" : "" >Outro</option>
        </select>

        <!-- Campo para CPF (não editável) -->
        <label for="cpf">CPF:</label>
        <input type="text" id="cpf" name="cpf" value="${cliente.cpf}" readonly>

        <!-- Campo para Email -->
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${cliente.email}" required>

        <!-- Campo para Senha -->
        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" value="${cliente.senha}" required>

        <!-- Botão para Salvar -->
        <button type="submit">Salvar Alterações</button>
    </form>
</body>
</html>
