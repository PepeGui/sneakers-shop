<!doctype html>
<html lang="pt-BR">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
                    <img src="${tenis.imagem}" alt="${tenis.nome}" class="product-image">
                    <h3>${tenis.nome}</h3>
                    <p>R$ ${tenis.preco}</p>
                    <a href="/Detalhes/detalhes.jsp?id=${tenis.id}">Ver Detalhes</a>

                    <!-- Formulário para adicionar ao carrinho -->
                    <form action="/adicionarCarrinho" method="POST" style="margin-top: 10px;">
                        <input type="hidden" name="id" value="${tenis.id}">
                        <label for="quantidade-${tenis.id}">Quantidade:</label>
                        <input type="number" id="quantidade-${tenis.id}" name="quantidade" value="1" min="1" style="width: 60px;">
                        <button type="submit">Adicionar ao Carrinho</button>
                    </form>
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
            <button onclick="window.location.href='/Cadastro-Cliente/cadastro-cliente.jsp'">Cadastrar-se</button>
            <button onclick="window.location.href='/Area-Cliente/AreaCliente.jsp'">Área do Cliente</button> <!-- Botão Adicionado -->
        </div>
    </div>

    <!-- Modal para login -->
    <div id="loginModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeLoginModal()">&times;</span>
            <h2>Login</h2>
            <form action="/login-cliente" method="POST">
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
