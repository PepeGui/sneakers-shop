<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tela Principal</title>
    <link rel="stylesheet" href="/Principal/principal.css">
</head>
<body>
<div class="login-container">
    <!-- Mensagem de boas-vindas com o nome do usuário -->
    <c:if test="${not empty sessionScope.usuarioNome}">
        <h1>Bem-vindo(a), ${sessionScope.usuarioNome}!</h1>
    </c:if>

    <!-- Botão para listar produtos -->
    <div>
        <form action="/find-all-tenis" method="GET">
            <button type="submit">Listar Produtos</button>
        </form>
    </div>

    <!-- Exibindo o botão "Listar Usuários" para administradores -->
    <c:if test="${not empty sessionScope.usuarioGrupo and sessionScope.usuarioGrupo eq 'Admin'}">
        <div>
            <a href="/find-all-usuarios"><button class="submit">Listar Usuários</button></a>
        </div>
    </c:if>

    <!-- Exibindo o botão "Listar Pedidos" para Estoquistas -->
    <c:if test="${not empty sessionScope.usuarioGrupo and sessionScope.usuarioGrupo eq 'Estoquista'}">
        <div>
            <a href="/find-all-pedidos"><button class="submit">Listar Pedidos</button></a>
        </div>
    </c:if>

    <!-- Botão de Logout -->
    <div>
        <form action="/logout-usuario" method="POST">
            <button type="submit">Logout</button>
        </form>
    </div>
</div>
</body>
</html>
