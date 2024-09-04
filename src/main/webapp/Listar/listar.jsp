<!DOCTYPE html>
<html lang="pt-br" xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100..900&display=swap" rel="stylesheet">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabela de Usuários</title>
    <link rel="stylesheet" href="listar.css">
</head>
<body>

<h3>Tabela de Usuários</h3>

<!-- Campo de Pesquisa -->
<div class="search-container">
    <form action="listarUsuarios.jsp" method="GET">
        <input type="text" id="search" name="search" placeholder="Pesquisar por nome ou email..." required>
        <button type="submit">Pesquisar</button>
    </form>
</div>

<!-- Botão de Cadastro -->
<div>
    <a href="../Cadastro-Usuario/Cadastro.html"><img src="../Img/Plus.png" alt="Cadastrar Usuário"></a>
</div>

<table cellspacing="0" cellpadding="0">
    <tr>
        <th>Nome</th>
        <th>Email</th>
        <th>Status</th>
        <th>Alterar</th>
        <th>Hab/Des</th>
        <th></th>
    </tr>
    <c:forEach var="usuario" items="${usuarios}">
        <tr>
            <td id="nome">${usuario.nome}</td>
            <td id="email">${usuario.email}</td>
            <td id="status">${usuario.ativo ? 'Ativo' : 'Inativo'}</td>
            <td id="alterar"><a href="../Alterar/alterar.html">Alterar</a></td>
            <td id="hab/des">
                <div class="switch__container">
                    <input id="switch-shadow-${usuario.id}" class="switch switch--shadow" type="checkbox" ${usuario.ativo ? 'checked' : ''} />
                    <label for="switch-shadow-${usuario.id}"></label>
                </div>
            </td>
            <td>
                <a href="/delete-usuario?id=${usuario.id}">
                    <button type="submit">Deletar</button>
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<div class="buttons-container">
    <a href="../Principal/principal.html"><button class="sherek">Voltar</button></a>
</div>

</body>
</html>
