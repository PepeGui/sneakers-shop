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
            <img src="/Img/User.png" alt="Usuário" title="Minha Conta" class="user-button" onclick="openUserOptions()">
        </div>
    </header>

    <section class="product-section">
        <h2>Nossos Tênis</h2>
        <div class="product-list">
            <c:forEach var="tenis" items="${tenis}">
                <div class="product-item">
                    <%--<!-- Exibe a imagem do tênis -->
                    <img id="imagem" src="/${tenis.imagem}" alt="${tenis.nome}" class="product-image">
                    <h3 class="product-name">${tenis.nome}</h3>
                    <p class="product-price">R$ ${tenis.preco}</p>
                    <!-- Link para os detalhes do produto -->
                    <a href="detalhes.jsp?id=${tenis.id}" class="product-details">Ver Detalhes</a>--%>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- Modal para as opções de usuário -->
    <div id="userModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeUserOptions()">&times;</span>
            <h2>Minha Conta</h2>
            <button onclick="openLoginModal()">Login</button>
            <button onclick="window.location.href='/cadastro-cliente'">Cadastrar-se</button>
        </div>
    </div>

    <!-- Modal para login -->
    <div id="loginModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeLoginModal()">&times;</span>
            <h2>Login</h2>
            <form action="loginServlet" method="POST">
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="senha" placeholder="Senha" required>
                <button type="submit">Entrar</button>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
    </footer>

    <script>
        function openUserOptions() {
            document.getElementById('userModal').style.display = 'flex';
        }

        function closeUserOptions() {
            document.getElementById('userModal').style.display = 'none';
        }

        function openLoginModal() {
            closeUserOptions();
            document.getElementById('loginModal').style.display = 'flex';
        }

        function closeLoginModal() {
            document.getElementById('loginModal').style.display = 'none';
        }
    </script>
</body>
</html>