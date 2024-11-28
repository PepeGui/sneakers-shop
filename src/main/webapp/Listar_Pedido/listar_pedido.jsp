<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedidos - Sneakers Shop</title>
    <link rel="stylesheet" href="/Listar_Pedido/listar_pedido.css">
</head>

<body>
    <header>
        <h1 class="brand-name">Sneakers-Shop</h1>
    </header>

    <section class="order-search">
        <div class="search-bar">
            <form action="/find-all-pedidos" method="get">
                <input type="text" name="pesquisa" placeholder="Pesquisar pedidos...">
                <button type="submit">Pesquisar</button>
            </form>
        </div>
    </section>

    <section class="order-table">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
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
                            <!-- Botão Visualizar -->
                            <button onclick="visualizarPedido('${pedido.id}', '${pedido.dataPedido}', '${pedido.status}', '${pedido.valorTotal}')">Visualizar</button>

                            <!-- Botão Aceitar Pedido -->
                            <form action="/alterarStatusPedido" method="POST" style="display:inline;">
                                <c:choose>
                                    <c:when test="${pedido.status == 'Pedido concluido'}">
                                        <button type="submit" disabled>Mudar Status</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit">Mudar Status</button>
                                    </c:otherwise>
                                </c:choose>
                                <input type="hidden" name="id" value="${pedido.id}">
                                <input type="hidden" name="status" value="${pedido.status}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>

    <!-- Modal para visualizar detalhes do pedido -->
    <div id="visualizar-modal" class="modal">
        <div class="modal-content">
            <h2>Detalhes do Pedido</h2>
            <p><strong>ID:</strong> <span id="modal-id"></span></p>
            <p><strong>Data:</strong> <span id="modal-data"></span></p>
            <p><strong>Status:</strong> <span id="modal-status"></span></p>
            <p><strong>Valor Total:</strong> R$ <span id="modal-valorTotal"></span></p>
            <button class="close-btn" onclick="fecharModal()">Fechar</button>
        </div>
    </div>

    <script>
        // Função para visualizar detalhes do pedido
        function visualizarPedido(id, data, status, valorTotal) {
            document.getElementById("modal-id").textContent = id;
            document.getElementById("modal-data").textContent = data;
            document.getElementById("modal-status").textContent = status;
            document.getElementById("modal-valorTotal").textContent = valorTotal;

            document.getElementById("visualizar-modal").style.display = "block";
        }

        // Função para fechar o modal
        function fecharModal() {
            document.getElementById("visualizar-modal").style.display = "none";
        }
    </script>

</body>
</html>
