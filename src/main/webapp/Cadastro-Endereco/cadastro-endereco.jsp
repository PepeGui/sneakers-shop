<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Endereço</title>
    <link rel="stylesheet" href="/Cadastro-Endereco/cadastro-endereco.css"> <!-- Verifique se o caminho está correto -->
</head>
<body>
    <div class="container">
        <h1>Cadastro de Endereço</h1>
        <form action="/cadastrar-endereco" method="POST">
            <label for="cep">CEP:</label>
            <input type="text" id="cep" name="cep" placeholder="Digite o CEP" required>

            <label for="logradouro">Logradouro:</label>
            <input type="text" id="logradouro" name="logradouro" placeholder="Digite o logradouro" required>

            <label for="numero">Número:</label>
            <input type="text" id="numero" name="numero" placeholder="Digite o número" required>

            <label for="bairro">Bairro:</label>
            <input type="text" id="bairro" name="bairro" placeholder="Digite o bairro" required>

            <label for="cidade">Cidade:</label>
            <input type="text" id="cidade" name="cidade" placeholder="Digite a cidade" required>

            <label for="uf">Estado (UF):</label>
            <input type="text" id="uf" name="uf" placeholder="Digite o estado" maxlength="2" required>

            <button type="submit">Cadastrar Endereço</button>
        </form>
    </div>
</body>
</html>
