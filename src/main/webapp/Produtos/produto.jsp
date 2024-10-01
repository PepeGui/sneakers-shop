<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneakers Shop</title>
    <link rel="stylesheet" href="/Produtos/produto.css">
</head>
<body>
<header>
    <h1 class="brand-name">Sneakers-Shop</h1>
</header>

<section class="product-search">
    <div class="add-product">
        <a href="/Novo-Produto/novo-produto.jsp"><button>Novo Produto</button></a>
    </div>
    <div class="search-bar">
        <form action="/find-all-tenis" method="get">
            <input type="text" name="pesquisa" placeholder="Pesquisar produtos...">
            <button type="submit">Pesquisar</button>
        </form>
    </div>
</section>

<section class="product-table">
    <table>
        <thead>
        <tr>
            <th>Código</th>
            <th>Nome</th>
            <th>Estoque</th>
            <th>Valor</th>
            <th>Status</th>
            <th>Opções</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tenis" items="${tenisList}">
            <tr>
                <td>${tenis.id}</td>
                <td>${tenis.nome}</td>
                <td>${tenis.estoque}</td>
                <td>R$ ${tenis.preco}</td>
                <td><input type="checkbox" ${tenis.ativo ? 'checked' : ''}> Ativo</td>
                <td><a href="/novo-produto.jsp?id=${tenis.id}"><button>Editar</button></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
