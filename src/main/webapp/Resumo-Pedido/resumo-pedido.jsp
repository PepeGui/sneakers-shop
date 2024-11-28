<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Resumo do Pedido</title>
    <link rel="stylesheet" href="/Resumo-Pedido/resumo-pedido.css">
</head>
<body>

<h2>Resumo do Pedido</h2>

<h3>Endereço de Entrega</h3>
<p>${enderecoSelecionado.logradouro}, ${enderecoSelecionado.numero} - ${enderecoSelecionado.bairro}, ${enderecoSelecionado.cidade} - ${enderecoSelecionado.uf}</p>
<p>CEP: ${enderecoSelecionado.cep}</p>

<h3>Produtos</h3>
<table>
    <thead>
        <tr>
            <th>Produto</th>
            <th>Quantidade</th>
            <th>Preço Unitário</th>
            <th>Total</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${itensCarrinho}">
            <tr>
                <td>${item.tenis.nome}</td>
                <td>${item.quantidade}</td>
                <td>R$ ${item.tenis.preco}</td>
                <td>R$ ${item.tenis.preco * item.quantidade}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<p>Subtotal: R$ ${subtotal}</p>
<p>Frete: R$ ${frete}</p>
<p>Total: R$ ${total}</p>

<h3>Forma de Pagamento</h3>
<p>${formaPagamento}</p>

<form action="${pageContext.request.contextPath}/finalizar-compra" method="POST">
    <input type="hidden" name="formaPagamento" value="${formaPagamento}">
    <button type="submit">Concluir Compra</button>
</form>

<form action="/Carrinho/carrinho.jsp" method="GET">
    <button type="submit">Voltar para o Carrinho</button>
</form>

</body>
</html>
