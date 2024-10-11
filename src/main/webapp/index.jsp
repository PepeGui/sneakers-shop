<!doctype html>
<html lang="pt-BR">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.Tenis, dao.TenisDao" %>
<%
    // Inicializa o TenisDao e carrega a lista de tênis
    TenisDao tenisDao = new TenisDao();
    List<Tenis> listaTenis = tenisDao.getAllTenis();
    request.setAttribute("tenis", listaTenis);
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="index.css">
    <title>Sneaker-Shop - Landing Page</title>
</head>
<body>
    <header>
        <h1>Sneaker-Shop</h1>
        <div class="header-icons">
            <img src="/Img/Carrinho.png" alt="Carrinho" title="Carrinho">
            <img src="/Img/User.png" alt="Usuário" title="Minha Conta">
        </div>
    </header>

    <section class="product-section">
        <h2>Nossos Tênis</h2>
        <div class="product-list">
            <c:forEach var="tenis" items="${tenis}">
                <div class="product-item">
                    <!-- Exibe a imagem do tênis -->
                    <img id="imagem" src="/${tenis.imagem}" alt="${tenis.nome}" class="product-image">
                    <h3 class="product-name">${tenis.nome}</h3>
                    <p class="product-price">R$ ${tenis.preco}</p>
                    <!-- Link para os detalhes do produto -->
                    <a href="detalhes.jsp?id=${tenis.id}" class="product-details">Ver Detalhes</a>
                </div>
            </c:forEach>
        </div>
    </section>

    <footer>
        <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
    </footer>
</body>
</html>
