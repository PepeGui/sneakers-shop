<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Estoque</title>
    <link rel="stylesheet" href="/AlterarEstoque/alterar-estoque.css">
    <style>
        .cadastro-container {
            width: 400px;
            margin: auto;
            padding: 20px;
            background-color: #f4f4f4;
            border-radius: 8px;
        }

        .cadastro-container h2 {
            text-align: center;
        }

        .cadastro-container div {
            margin-bottom: 10px;
        }

        .cadastro-container label {
            display: block;
            font-weight: bold;
        }

        .cadastro-container input,
        .cadastro-container button {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
        }

        .back-button {
            text-align: center;
            margin-top: 20px;
        }

        .back-button a {
            text-decoration: none;
            color: #007BFF;
        }
    </style>
</head>
<body>

<div class="cadastro-container">
    <h2>Alterar Estoque</h2>
    <form action="/AlterarEstoque" method="POST">
        <!-- Campo oculto para o ID do tênis -->
        <input type="hidden" name="id" value="${tenis.id}">

        <!-- Campo de Estoque -->
        <div>
            <label for="estoque">Estoque Atual:</label>
            <input type="number" name="estoque" value="${tenis.estoque}" min="0" step="1">
        </div>

        <button type="submit">Salvar Alterações</button>
    </form>

    <div class="back-button">
        <a href="<%= request.getContextPath() %>/find-all-tenis">Voltar</a>
    </div>
</div>

</body>
</html>
