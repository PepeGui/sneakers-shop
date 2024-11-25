<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Área do Cliente</title>
    <link rel="stylesheet" href="/Area-Cliente/AreaCliente.css">

</head>
<body>
<h2>Meus Pedidos</h2>

<c:if test="${not empty pedidos}">
    <table border="1">
        <thead>
            <tr>
                <th>ID do Pedido</th>
                <th>Data</th>
                <th>Status</th>
                <th>Valor Total</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="pedido" items="${pedidos}">
                <tr>
                    <td>${pedido.id}</td>
                    <td>${pedido.dataPedido}</td>
                    <td>${pedido.status}</td>
                    <td>R$ ${pedido.valorTotal}</td>
                    <td>
                        <form method="GET" action="/detalhes-pedido">
                            <input type="hidden" name="pedidoId" value="${pedido.id}">
                            <button type="submit">Ver Detalhes</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty pedidos}">
    <p>Você ainda não fez nenhum pedido.</p>
</c:if>
</body>
</html>
