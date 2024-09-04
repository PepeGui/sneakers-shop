<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro</title>
    <link rel="stylesheet" href="Cadastro.css">
</head>
<body>
<div class="cadastro-container">
    <h2>Cadastro</h2>
    <form action="cadastrarUsuario.jsp" method="POST">
        <div class="input-group">
            <label for="nome">Nome completo</label>
            <input type="text" id="nome" name="nome" required>
        </div>
        <div class="input-group">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="input-group">
            <label for="cpf">CPF</label>
            <input type="text" id="cpf" name="cpf" required>
        </div>
        <div class="input-group">
            <label for="grupo">Grupo</label>
            <input type="text" id="grupo" name="grupo" required>
        </div>
        <button type="submit">Finalizar</button>
    </form>
</div>
</body>
</html>
