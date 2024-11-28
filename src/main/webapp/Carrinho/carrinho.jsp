<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneaker-Shop - Carrinho</title>
    <link rel="stylesheet" href="/Carrinho/carrinho.css">
</head>
<body>
<header>
    <h1>Sneaker-Shop</h1>
    <nav>
        <ul>
            <li><a href="../../index.jsp">Home</a></li>
            <li><a href="/carrinho">Carrinho</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Carrinho de Compras</h2>
    <form action="/atualizarCarrinho" method="POST">
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Produto</th>
                    <th>Quantidade</th>
                    <th>Preço Unitário</th>
                    <th>Total</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${itensCarrinho}">
                    <tr>
                        <td>${item.tenis.nome}</td>
                        <td>
                            <input type="number" name="quantidade-${item.tenis.id}" value="${item.quantidade}" min="1">
                        </td>
                        <td><fmt:formatNumber value="${item.tenis.preco}" type="currency" /></td>
                        <td>
                            <fmt:formatNumber value="${item.tenis.preco * item.quantidade}" type="currency" />
                        </td>
                        <td>
                            <form action="/removerCarrinho" method="post" style="display: inline;">
                                <input type="hidden" name="tenisId" value="${item.tenis.id}">
                                <button type="submit" class="remove-btn">Remover</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="cart-summary">
            <p>Subtotal: <span>${subtotal}</span></p>
            <p>Frete: <span>${frete}</span></p>
            <p>Total: <strong>${total}</strong></p>
            <button type="submit" class="update-cart-btn">Atualizar Carrinho</button>
        </div>
    </form>

    <a href="/find-all-enderecos-cliente" class="checkout-btn">Finalizar Compra</a>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
