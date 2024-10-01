<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Produto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Novo-Produto/novo-produto.css">
</head>
<body>

<div class="cadastro-container">
    <h2>Cadastro de Produto</h2>
    <!-- O form agora usa o contexto do servlet -->
    <form action="<%= request.getContextPath() %>/cadastrarTenis" method="POST" enctype="multipart/form-data">
        <div class="input-group">
            <label for="nome">Nome do Produto</label>
            <input type="text" id="nome" name="nome" required>
        </div>
        <div class="input-group">
            <label for="preco">Preço</label>
            <input type="number" id="preco" name="preco" required>
        </div>
        <div class="input-group">
            <label for="estoque">Estoque</label>
            <input type="number" id="estoque" name="estoque" required>
        </div>
        <div class="input-group">
            <label for="descricao">Descrição do Produto</label>
            <input type="text" id="descricao" name="descricao" required>
        </div>
        <div class="input-group">
            <label for="avaliacao">Avaliação</label>
            <input type="text" id="avaliacao" name="avaliacao" required>
        </div>

        <!-- Campo para upload de imagem -->
        <div class="input-group">
            <label for="imagem">Adicionar Imagem</label>
            <input type="file" id="imagem" name="imagem" accept="image/*" required>
        </div>

        <button type="submit">Finalizar</button>
    </form>


    <div class="back-button">
        <a href="<%= request.getContextPath() %>/Produtos/produto.jsp">Voltar</a>
    </div>
</div>

</body>
</html>
