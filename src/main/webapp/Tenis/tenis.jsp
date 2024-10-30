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
    <title>Sneakers Shop</title>
    <link rel="stylesheet" href="/Tenis/tenis.css">
</head>

<script src="/Tenis/tenis.js"></script>
<script>
    function alterarStatus(id, status) {
        const xhr = new XMLHttpRequest();
        const url = "/alterarStatusTenis";  // O servlet que processa a mudança de status
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    console.log("Status alterado com sucesso!");
                } else {
                    alert("Erro ao alterar status!");
                }
            }
        };

        // Envia o ID do tênis e o novo status (true para ativo, false para inativo)
        xhr.send("id=" + id + "&status=" + (status ? "ativo" : "inativo"));
    }
</script>

<body>
    <header>
        <h1 class="brand-name">Sneakers-Shop</h1>
    </header>

    <section class="product-search">
        <div class="add-product">
            <a href="/Novo-Tenis/novo-tenis.jsp"><button>Novo Produto</button></a>
        </div>
        <div class="search-bar">
            <form action="/find-all-tenis" method="get">
                <input type="text" name="pesquisa" placeholder="Pesquisar tênis...">
                <button type="submit">Pesquisar</button>
            </form>
        </div>
    </section>

    <section class="product-table">
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th>Estoque</th>
                    <th>Valor</th>
                    <th>Status</th>
                    <th>Hab/Des</th>
                    <th>Opções</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="tenis" items="${tenisList}">
                    <tr>
                        <td>${tenis.id}</td>
                        <td>${tenis.nome}</td>
                        <td>${tenis.estoque}</td>
                        <td>R$ ${tenis.preco}</td>
                        <td id="status">${tenis.ativo ? 'Ativo' : 'Inativo'}</td>
                        <td id="hab/des">
                            <div class="switch__container">
                                <input id="switch-shadow-${tenis.id}" class="switch switch--shadow" type="checkbox"
                                       ${tenis.ativo ? 'checked' : ''}
                                       onchange="alterarStatus(${tenis.id}, this.checked)" />
                                <label for="switch-shadow-${tenis.id}"></label>
                            </div>
                        </td>
                        <td>
                            <a href="/tela-alterarTenis?id=${tenis.id},grupo=${grupo}"><button>Editar</button></a>
                            <!-- Passa o caminho da imagem, nome, avaliação e descrição para a função -->
                            <c:if test="${grupo == 'Admin'}">
                                <button onclick="visualizarTenis('${tenis.imagem}', '${tenis.nome}', '${tenis.avaliacao}', '${tenis.descricao}')">Visualizar</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </section>

    <!-- Modal para exibir a imagem, nome, avaliação e descrição -->
    <div id="visualizar-modal" class="modal">
        <div class="modal-content">
            <img id="modal-imagem" src="" alt="Imagem do tênis">
            <p id="modal-nome"></p>
            <p id="modal-avaliacao"></p>
            <p id="modal-descricao"></p>
            <button class="close-btn" onclick="fecharModal()">Fechar</button>
        </div>
    </div>

</body>
</html>
