<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Produto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Novo-Produto/novo-produto.css">
</head>
<body>
<div class="cadastro-container">
    <h2>Alterar</h2>
    <form action="/alterar-Tenis" method="POST">
        <div class="input-group">
            <label for="nome">Nome</label>
            <input type="text" id="nome" name="nome" value = ${tenis.nome}  required>
        </div>
        <div class="input-group">
            <label for="preco">Preço</label>
            <input type="number" id="preco" name="preco" value = ${tenis.preco} required>
        </div>
        <div class="input-group">
            <label for="estoque">Estoque</label>
            <input type="number" id="estoque" name="estoque" value = ${tenis.estoque} required>
        </div>
        <div class="input-group">
                    <label for="descricao">Descrição</label>
                    <input type="text" id="descricao" name="descricao" value = ${tenis.descricao} required>
        </div>
        <div class="input-group">
            <label for="avaliacao">Avaliação</label>
            <input type="text" id="avaliacao" name="avaliacao" value = ${tenis.avaliacao} required>
        </div>

        <input type="hidden" id="id" name="id" value = ${tenis.id}>
        <button type="submit">Finalizar</button>
    </form>
</div>
</body>
</html>