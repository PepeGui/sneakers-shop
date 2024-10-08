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

    <title>Tabela de Usuários</title>
    <link rel="stylesheet" href="/Listar/listar.css">
</head>
<script>
    function alterarStatus(id, status) {
        const xhr = new XMLHttpRequest();
        const url = "/alterarStatus";  // O servlet que processa a mudança de status
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

        // Envia o ID do usuário e o novo status (true para ativo, false para inativo)
        xhr.send("id=" + id + "&status=" + (status ? "ativo" : "inativo"));
    }
</script>

<body>

<h3>Tabela de Usuários</h3>

<!-- Campo de Pesquisa -->
<div class="search-container">
    <form action="/find-all-usuarios" method="GET">
        <input type="text" id="pesquisa" name="pesquisa" placeholder="Pesquisar por nome ou email..." required>
        <button type="submit">Pesquisar</button>
    </form>
</div>

<!-- Botão de Cadastro -->
<div>
    <a href="/Cadastro-Usuario/Cadastro.jsp"><img src="/Img/Plus.png" alt="Cadastrar Usuário"></a>
</div>

<table cellspacing="0" cellpadding="0">
    <tr>
        <th>Nome</th>
        <th>Email</th>
        <th>Alterar</th>
        <th>Grupo</th>
        <th>Status</th>
        <th>Hab/Des</th>
        <th></th>
    </tr>
    <c:forEach var="usuario" items="${usuarios}">
        <tr>
            <td id="nome">${usuario.nome}</td>
            <td id="email">${usuario.email}</td>
            <td id="status">${usuario.ativo ? 'Ativo' : 'Inativo'}</td>
            <td id="grupo">${usuario.grupo}</td>
            <td id="alterar"><a href="/tela-alterar?id=${usuario.id}">Alterar</a></td>
            <td id="hab/des">
                <div class="switch__container">
                    <input id="switch-shadow-${usuario.id}" class="switch switch--shadow" type="checkbox"
                           ${usuario.ativo ? 'checked' : ''}
                           onchange="alterarStatus(${usuario.id}, this.checked)" />
                    <label for="switch-shadow-${usuario.id}"></label>
                </div>
            </td>
        </tr>
    </c:forEach>

</table>

<div class="buttons-container">
    <a href="/Principal/principal.jsp"><button class="sherek">Voltar</button></a>
</div>

</body>
</html>
