<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.CarrinhoDao" %>
<%@ page import="model.ItemCarrinho" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    CarrinhoDao carrinhoDao = new CarrinhoDao();
    List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho();
    request.setAttribute("itensCarrinho", itensCarrinho);
    DecimalFormat df = new DecimalFormat("#,##0.00");
    double subtotal = 0.0;
     for(ItemCarrinho item : itensCarrinho){
           subtotal += item.getTenis().getPreco() * item.getQuantidade();
    }
    double total = subtotal + 30.0;
    request.setAttribute("subtotal", subtotal);
    request.setAttribute("total", total);
%>
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
            <li><a href="/carrinho.jsp">Carrinho</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Carrinho de Compras</h2>
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
                    <td><input type="number" value="${item.quantidade}" min="1" class="quantity-input"></td>
                    <td> <fmt:formatNumber value="${item.tenis.preco}" type="currency" /></td>
                    <td> <fmt:formatNumber value="${item.tenis.preco * item.quantidade}" type="currency" /></td>
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
        <p>Subtotal: <span> ${subtotal}</span></p> <!-- Inicialmente zero, será atualizado com JavaScript -->
        <p>Frete: <span>R$ 30,00</span></p>
        <p>Total: <strong> ${total}</strong></p> <!-- Inicialmente zero -->
        <a href="/find-all-enderecos-cliente" class="checkout-btn">Finalizar Compra</a>


    </div>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>

</body>
</html>
