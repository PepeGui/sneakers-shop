<!DOCTYPE html>
<html lang="pt-BR">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Tenis, model.ImagemTenis, dao.TenisDao, java.util.List" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    TenisDao tenisDao = new TenisDao();
    Tenis tenis = tenisDao.getTenisById(id);
    List<ImagemTenis> imagens = tenisDao.getImagensPorTenisId(id);
    request.setAttribute("tenis", tenis);
    request.setAttribute("imagens", imagens);
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Produto - Sneaker-Shop</title>
    <link rel="stylesheet" href="/Detalhes/detalhes.css">
</head>
<body>
<header>
    <h1>Sneaker-Shop</h1>
    <div class="header-icons">
        <a href="/carrinho.jsp">
            <img src="/Img/Carrinho.png" alt="Carrinho" title="Carrinho">
        </a>
        <a href="/minhaConta.jsp">
            <img src="/Img/User.png" alt="Usuário" title="Minha Conta">
        </a>
    </div>
</header>

<section class="product-details">
    <div class="product-details-container">
        <div class="product-image-container">
            <c:forEach var="imagemTenis" items="${imagens}">
                <c:if test="${imagemTenis.principal}">
                    <img id="imagemPrincipal" src="/${imagemTenis.caminho}" alt="${tenis.nome}" class="product-image">
                </c:if>
            </c:forEach>
        </div>

        <div class="secondary-images-container">
            <c:forEach var="imagemTenis" items="${imagens}">
                <c:if test="${!imagemTenis.principal}">
                    <img src="/${imagemTenis.caminho}" alt="${tenis.nome}" class="secondary-image">
                </c:if>
            </c:forEach>
        </div>

        <div class="product-info">
            <h2>${tenis.nome}</h2>
            <p class="product-price">R$ ${tenis.preco}</p>
            <p class="product-description">${tenis.descricao}</p>

            <!-- Formulário para adicionar ao carrinho -->
            <form action="/adicionarCarrinho" method="post">
                <input type="hidden" name="id" value="${tenis.id}">
                <label for="quantidade">Quantidade:</label>
                <input type="number" id="quantidade" name="quantidade" value="1" min="1" required>
                <button type="submit" class="add-to-cart-btn">Adicionar ao Carrinho</button>
            </form>
        </div>
    </div>
</section>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
