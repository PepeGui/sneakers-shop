<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Tenis</title>
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
    <h2>Cadastro de Produto</h2>
    <form action="<%= request.getContextPath() %>/cadastrarTenis" method="POST" enctype="multipart/form-data">
        <div class="input-group">
            <label for="nome">Nome do Tenis</label>
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

        <!-- Campo para upload de imagens -->
        <div class="input-group">
            <label for="imagem">Adicionar Imagens</label>
            <input type="file" id="imagem" name="imagem" accept="image/*" multiple required onchange="previewImages()">
        </div>

        <!-- Área para mostrar as pré-visualizações das imagens -->
        <div class="image-preview-container" id="previewContainer"></div>

        <!-- Campo oculto para armazenar a imagem principal selecionada -->
        <input type="hidden" id="principalImage" name="principalImage">

        <button type="submit">Finalizar</button>
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
                img.setAttribute('data-index', index);

                // Clique na imagem para definir como principal
                img.addEventListener('click', () => {
                    document.querySelectorAll('.image-preview-container img').forEach(image => {
                        image.classList.remove('selected');
                    });
                    img.classList.add('selected');
                    document.getElementById('principalImage').value = index;  // Define o índice da imagem principal
                });

                previewContainer.appendChild(img);
            };

            reader.readAsDataURL(file);  // Converte a imagem em base64 para exibição
        });
    }
</script>

</body>
</html>
