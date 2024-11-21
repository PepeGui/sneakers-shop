<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.CarrinhoDao" %>
<%@ page import="dao.EnderecoDao" %>
<%@ page import="model.ItemCarrinho" %>
<%@ page import="model.Endereco" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    // Recupera os itens do carrinho
    CarrinhoDao carrinhoDao = new CarrinhoDao();
    List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho();
    request.setAttribute("itensCarrinho", itensCarrinho);

    // Calcula subtotal e total
    DecimalFormat df = new DecimalFormat("#,##0.00");
    double subtotal = 0.0;
    for (ItemCarrinho item : itensCarrinho) {
        subtotal += item.getTenis().getPreco() * item.getQuantidade();
    }
    double total = subtotal + 30.0; // Frete fixo de 30
    request.setAttribute("subtotal", subtotal);
    request.setAttribute("total", total);

    // Recupera os endereços
    List<Endereco> enderecos = (List<Endereco>) request.getAttribute("enderecos");
%>

<!-- HTML do carrinho e endereços -->

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneaker-Shop - Finalizar Compra</title>
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
    <h2>Finalizar Compra</h2>

    <!-- Exibir os itens do carrinho -->
    <table class="cart-table">
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
                    <td><fmt:formatNumber value="${item.tenis.preco}" type="currency" /></td>
                    <td><fmt:formatNumber value="${item.tenis.preco * item.quantidade}" type="currency" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="cart-summary">
        <p>Subtotal: <fmt:formatNumber value="${subtotal}" type="currency" /></p>
        <p>Frete: <span>R$ 30,00</span></p>
        <p>Total: <strong><fmt:formatNumber value="${total}" type="currency" /></strong></p>
    </div>

    <!-- Exibir os endereços cadastrados -->
    <h3>Escolha o seu Endereço de Entrega</h3>
    <c:if test="${not empty enderecos}">
        <form action="/confirmar-compra" method="POST">
            <table border="1">
                <thead>
                    <tr>
                        <th>CEP</th>
                        <th>Logradouro</th>
                        <th>Número</th>
                        <th>Bairro</th>
                        <th>Cidade</th>
                        <th>UF</th>
                        <th>Selecionar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="endereco" items="${enderecos}">
                        <tr>
                            <td>${endereco.cep}</td>
                            <td>${endereco.logradouro}</td>
                            <td>${endereco.numero}</td>
                            <td>${endereco.bairro}</td>
                            <td>${endereco.cidade}</td>
                            <td>${endereco.uf}</td>
                            <td><input type="radio" name="enderecoId" value="${endereco.id}" required></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit">Confirmar Compra</button>
        </form>
    </c:if>

    <c:if test="${empty enderecos}">
        <p>Você não possui endereços cadastrados. <a href="/Cadastro-Endereco/cadastro-endereco.jsp">Cadastrar um novo endereço</a></p>
    </c:if>

</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>

</body>
</html>
