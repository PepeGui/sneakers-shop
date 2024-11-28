<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Produto</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/Novo-Tenis/novo-tenis.css">
    <style>
        .image-preview-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        .image-preview-container img {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border: 2px solid transparent;
            cursor: pointer;
        }
        .image-preview-container img.selected {
            border-color: green;
        }
    </style>
</head>
<body>

<div class="cadastro-container">
    <h2>Alterar Produto</h2>
    <form action="/alterar-tenis" method="POST" enctype="multipart/form-data">
        <!-- Campo oculto para o ID do tênis -->
        <input type="hidden" name="id" value="${tenis.id}">
        <!-- Campo oculto para o índice da imagem principal -->
        <input type="hidden" id="principalImageId" name="principalImageId" value="">

        <!-- Campos de informações do produto -->
        <div>
            <label for="nome">Nome:</label>
            <input type="text" name="nome" value="${tenis.nome}">
        </div>
        <div>
            <label for="preco">Preço:</label>
            <input type="text" name="preco" value="${tenis.preco}">
        </div>
        <div>
            <label for="descricao">Descrição:</label>
            <textarea name="descricao">${tenis.descricao}</textarea>
        </div>

        <!-- Campo de Avaliação -->
        <div>
            <label for="avaliacao">Avaliação:</label>
            <input type="number" name="avaliacao" value="${tenis.avaliacao}" min="0" max="5" step="0.1">
        </div>

        <!-- Campo de Estoque -->
        <div>
            <label for="estoque">Estoque:</label>
            <input type="number" name="estoque" value="${tenis.estoque}" min="0" step="1">
        </div>

        <!-- Input de múltiplas imagens -->
        <div>
            <label for="imagem">Imagens:</label>
            <input type="file" id="imagem" name="imagens[]" multiple onchange="previewImages()">
        </div>

        <!-- Contêiner para pré-visualizações -->
        <div class="image-preview-container" id="previewContainer"></div>

        <button type="submit">Salvar Alterações</button>
    </form>

    <div class="back-button">
        <a href="<%= request.getContextPath() %>/find-all-tenis">Voltar</a>
    </div>
</div>

<!-- JavaScript para pré-visualização e seleção da imagem principal -->
<script>
    function previewImages() {
        const previewContainer = document.getElementById('previewContainer');
        const files = document.getElementById('imagem').files;
        previewContainer.innerHTML = '';  // Limpa as pré-visualizações anteriores

        Array.from(files).forEach((file, index) => {
            const reader = new FileReader();

            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.setAttribute('data-index', index);  // Atribui o índice
                img.setAttribute('data-filename', file.name);  // Atribui o nome do arquivo

                // Clique na imagem para definir como principal
                img.addEventListener('click', () => {
                    document.querySelectorAll('.image-preview-container img').forEach(image => {
                        image.classList.remove('selected');
                    });
                    img.classList.add('selected');
                    document.getElementById('principalImageId').value = index;  // Define o índice como ID da imagem principal
                });

                previewContainer.appendChild(img);
            };

            reader.readAsDataURL(file);  // Converte a imagem em base64 para exibição
        });
    }
</script>

</body>
</html>
