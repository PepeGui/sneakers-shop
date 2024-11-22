<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.PedidoDao" %>
<%@ page import="dao.PedidoItemDao" %>
<%@ page import="dao.EnderecoDao" %>
<%@ page import="model.Pedido" %>
<%@ page import="model.PedidoItem" %>
<%@ page import="model.Endereco" %>
<%@ page import="java.util.List" %>

<%
    // Recupera o ID do pedido passado como parâmetro
    int pedidoId = Integer.parseInt(request.getParameter("pedidoId"));

    // DAOs para acessar dados
    PedidoDao pedidoDao = new PedidoDao();
    PedidoItemDao pedidoItemDao = new PedidoItemDao();
    EnderecoDao enderecoDao = new EnderecoDao();

    // Obtém os dados do pedido
    Pedido pedido = pedidoDao.obterPedidoPorId(pedidoId);
    List<PedidoItem> itensPedido = pedidoItemDao.obterItensPorPedido(pedidoId);
    Endereco enderecoEntrega = enderecoDao.obterEnderecoPorId(pedido.getEnderecoEntregaId());

    // Calcula o subtotal e o total do pedido
    double subtotal = 0.0;
    for (PedidoItem item : itensPedido) {
        subtotal += item.getPrecoUnitario() * item.getQuantidade();
    }
    double total = subtotal + 30.0; // Frete fixo de 30
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Pedido</title>
    <link rel="stylesheet" href="AreaCliente.css">
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
                    <td>${item.tenis.nome}</td>
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
        <p><strong>Complemento:</strong> ${enderecoEntrega.complemento}</p>
    </div>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
