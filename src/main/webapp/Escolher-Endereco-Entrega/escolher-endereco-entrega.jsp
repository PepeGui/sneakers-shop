<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneaker-Shop - Finalizar Compra</title>
    <!-- Link para o CSS externo -->
    <link rel="stylesheet" href="/Escolher-Endereco-Entrega/escolher-endereco-entrega.css">
</head>
<body>
<header>
    <h1>Sneaker-Shop</h1>
    <nav>
        <ul>
            <li><a href="../../index.jsp">Home</a></li>
            <li><a href="/Carrinho/carrinho.jsp">Carrinho</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Finalizar Compra</h2>

    <!-- Botão fixo para cadastrar endereço -->
    <div class="endereco-actions">
        <a href="/Cadastro-Endereco/cadastro-endereco.jsp" class="btn-cadastrar">Cadastrar Novo Endereço</a>
    </div>

    <!-- Exibir os endereços cadastrados -->
    <h3>Escolha o seu Endereço de Entrega</h3>
    <c:if test="${not empty enderecos}">
        <form action="/confirmar-compra" method="POST">
            <table class="enderecos-table">
                <thead>
                    <tr>
                        <th>CEP</th>
                        <th>Logradouro</th>
                        <th>Número</th>
                        <th>Bairro</th>
                        <th>Cidade</th>
                        <th>UF</th>
                        <th>Selecionar</th>
                        <th>Ações</th>
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
                            <!-- Botão para remover endereço -->
                            <td>
                                <form action="/removerEnderecoEntrega" method="POST" style="display:inline;">
                                    <input type="hidden" name="enderecoId" value="${endereco.id}">
                                    <button type="submit" class="btn-remover">Remover</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit">Confirmar Compra</button>
        </form>
    </c:if>

    <c:if test="${empty enderecos}">
        <p>Você não possui endereços cadastrados. Use o botão acima para adicionar um novo endereço.</p>
    </c:if>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
