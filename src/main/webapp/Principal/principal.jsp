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
        <form action="listarProdutos.jsp" method="GET">
            <button type="submit">Listar Produtos</button>
        </form>
    </div>

    <div>
        <a href="/find-all-usuarios"><button class="submit">Listar Usuários</button></a>
    </div>

    <div>
        <form action="listarPedidos.jsp" method="GET">
            <button type="submit">Listar Pedidos</button>
        </form>
    </div>
</div>
</body>
</html>
