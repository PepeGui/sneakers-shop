<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Produto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Novo-Tenis/novo-tenis.css">
</head>
<body>
<div class="cadastro-container">
    <h2>Alterar</h2>
    <form action="/alterar-Tenis" method="POST" enctype="multipart/form-data">
        <c:if test="${grupo == 'Admin' || grupo == null}">
            <div class="input-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" value="${tenis.nome}" required>
            </div>
            <div class="input-group">
                <label for="preco">Preço</label>
                <input type="number" id="preco" name="preco" value="${tenis.preco}" required>
            </div>
        </c:if>
        <div class="input-group">
            <label for="estoque">Estoque</label>
            <input type="number" id="estoque" name="estoque" value="${tenis.estoque}" required>
        </div>
        <c:if test="${grupo == 'Admin' || grupo == null}">
            <div class="input-group">
                <label for="descricao">Descrição</label>
                <input type="text" id="descricao" name="descricao" value="${tenis.descricao}" required>
            </div>
            <div class="input-group">
                <label for="avaliacao">Avaliação</label>
                <input type="text" id="avaliacao" name="avaliacao" value="${tenis.avaliacao}" required>
            </div>
            <div class="input-group">
                <label for="imagemAtual">Imagem Atual</label>
                <img src="<%= request.getContextPath() %>/uploads/${tenis.imagem}" alt="Imagem Atual" style="max-width: 150px; max-height: 150px;">
            </div>
            <div class="input-group">
                <label for="novaImagem">Nova Imagem</label>
                <input type="file" id="novaImagem" name="novaImagem">
            </div>
        </c:if>
        <input type="hidden" id="id" name="id" value="${tenis.id}">
        <input type="hidden" id="grupo" name="grupo" value="${grupo}">
        <button type="submit">Finalizar</button>
    </form>
</div>
</body>
</html>
