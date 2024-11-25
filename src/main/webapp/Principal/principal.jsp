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
        <div>
            <form action="/find-all-tenis" method="GET">
                <button type="submit">Listar Produtos</button>
            </form>
        </div>
    <c:if test="${grupo eq 'Admin'}">
        <div>
            <a href="/find-all-usuarios"><button class="submit">Listar Usuários</button></a>
        </div>
    </c:if>
    <c:if test="${grupo == 'Estoquista'}">
        <div>
            <form action="/Listar_Pedido/listar_pedido.jsp" method="GET">
                <button type="submit">Listar Pedidos</button>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
