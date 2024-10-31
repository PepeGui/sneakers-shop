<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <li><a href="index.html">Home</a></li>
            <li><a href="produtos.html">Produtos</a></li>
            <li><a href="carrinho.html">Carrinho</a></li>
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
                    <td>R$ ${df.format(item.tenis.preco)}</td>
                    <td>R$ ${df.format(item.tenis.preco * item.quantidade)}</td>
                    <td><button class="remove-btn">Remover</button></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="cart-summary">
        <p>Subtotal: <span>R$ 0,00</span></p> <!-- Inicialmente zero, será atualizado com JavaScript -->
        <p>Frete: <span>R$ 30,00</span></p>
        <p>Total: <strong>R$ 0,00</strong></p> <!-- Inicialmente zero -->
        <button class="checkout-btn">Finalizar Compra</button>
    </div>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>

<script>
    // Lógica de cálculo do subtotal e total
    function updateCartSummary() {
        let subtotal = 0;
        document.querySelectorAll('.cart-table tbody tr').forEach(row => {
            const price = parseFloat(row.cells[2].innerText.replace('R$ ', '').replace('.', '').replace(',', '.'));
            const quantity = parseInt(row.querySelector('.quantity-input').value);
            subtotal += price * quantity;
        });

        const shipping = 30.00; // Exemplo de valor de frete fixo
        const total = subtotal + shipping;

        document.querySelector('.cart-summary span').innerText = `R$ ${subtotal.toFixed(2).replace('.', ',')}`;
        document.querySelector('.cart-summary strong').innerText = `R$ ${total.toFixed(2).replace('.', ',')}`;
    }

    document.addEventListener('DOMContentLoaded', updateCartSummary); // Atualiza o resumo ao carregar a página
</script>
</body>
</html>
