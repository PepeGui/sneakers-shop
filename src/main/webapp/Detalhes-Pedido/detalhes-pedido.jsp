<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Pedido</title>
    <link rel="stylesheet" href="/Area-Cliente/AreaCliente.css">
</head>
<body>
<header>
    <h1>Sneaker-Shop</h1>
    <nav>
        <ul>
            <li><a href="../../index.jsp">Home</a></li>
            <li><a href="/Area-Cliente/area-cliente.jsp">Meus Pedidos</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Detalhes do Pedido</h2>

    <!-- Informações Gerais do Pedido -->
    <div class="pedido-info">
        <p><strong>ID do Pedido:</strong> ${pedido.id}</p>
        <p><strong>Data do Pedido:</strong> <fmt:formatDate value="${pedido.dataPedido}" pattern="dd/MM/yyyy HH:mm" /></p>
        <p><strong>Status:</strong> ${pedido.status}</p>
        <p><strong>Total:</strong> <fmt:formatNumber value="${total}" type="currency" /></p>
    </div>

    <!-- Itens do Pedido -->
    <h3>Itens do Pedido</h3>
    <table class="order-table">
        <thead>
            <tr>
                <th>Produto</th>
                <th>Quantidade</th>
                <th>Preço Unitário</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${itensPedido}">
                <tr>
                    <td>${item.quantidade}</td>
                    <td><fmt:formatNumber value="${item.precoUnitario}" type="currency" /></td>
                    <td><fmt:formatNumber value="${item.precoUnitario * item.quantidade}" type="currency" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Endereço de Entrega -->
    <h3>Endereço de Entrega</h3>
    <div class="endereco-info">
        <p><strong>CEP:</strong> ${enderecoEntrega.cep}</p>
        <p><strong>Logradouro:</strong> ${enderecoEntrega.logradouro}, ${enderecoEntrega.numero}</p>
        <p><strong>Bairro:</strong> ${enderecoEntrega.bairro}</p>
        <p><strong>Cidade:</strong> ${enderecoEntrega.cidade} - ${enderecoEntrega.uf}</p>
    </div>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
