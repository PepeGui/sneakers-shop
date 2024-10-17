<!doctype html>
<html lang="pt-BR">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.Tenis, dao.TenisDao" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - Sneaker-Shop</title>
    <link rel="stylesheet" href="cadastro-cliente.css">
</head>
<body>
    <h2>Cadastro de Cliente</h2>
    <form action="${pageContext.request.contextPath}/cadastro-cliente" method="POST">

        <label for="nome">Nome Completo:</label>
        <input type="text" id="nome" name="nome" required pattern="[A-Za-z]{3,} [A-Za-z]{3,}" title="O nome deve conter pelo menos duas palavras com 3 letras cada.">

        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" id="dataNascimento" name="dataNascimento" required>

        <label for="genero">Gênero:</label>
        <select id="genero" name="genero" required>
            <option value="masculino">Masculino</option>
            <option value="feminino">Feminino</option>
            <option value="outro">Outro</option>
        </select>

        <label for="cpf">CPF:</label>
        <input type="text" id="cpf" name="cpf" required pattern="\d{11}" title="Digite um CPF válido (apenas números).">

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <h3>Endereço de Entrega</h3>
        <div id="enderecosEntrega">
            <div class="enderecoEntrega">
                <label for="cepEntrega">CEP:</label>
                <input type="text" id="cepEntrega" name="cepEntrega[]" required pattern="\d{5}-\d{3}" title="Digite um CEP válido (formato 00000-000).">

                <label for="logradouroEntrega">Logradouro:</label>
                <input type="text" id="logradouroEntrega" name="logradouroEntrega[]" required>

                <label for="numeroEntrega">Número:</label>
                <input type="text" id="numeroEntrega" name="numeroEntrega[]" required>

                <label for="complementoEntrega">Complemento:</label>
                <input type="text" id="complementoEntrega" name="complementoEntrega[]">

                <label for="bairroEntrega">Bairro:</label>
                <input type="text" id="bairroEntrega" name="bairroEntrega[]" required>

                <label for="cidadeEntrega">Cidade:</label>
                <input type="text" id="cidadeEntrega" name="cidadeEntrega[]" required>

                <label for="ufEntrega">UF:</label>
                <select id="ufEntrega" name="ufEntrega[]" required>
                    <option value="AC">AC</option>
                    <option value="AL">AL</option>
                    <option value="AP">AP</option>
                    <option value="AM">AM</option>
                    <option value="BA">BA</option>
                    <option value="CE">CE</option>
                    <option value="DF">DF</option>
                    <option value="ES">ES</option>
                    <option value="GO">GO</option>
                    <option value="MA">MA</option>
                    <option value="MT">MT</option>
                    <option value="MS">MS</option>
                    <option value="MG">MG</option>
                    <option value="PA">PA</option>
                    <option value="PB">PB</option>
                    <option value="PR">PR</option>
                    <option value="PE">PE</option>
                    <option value="PI">PI</option>
                    <option value="RJ">RJ</option>
                    <option value="RN">RN</option>
                    <option value="RS">RS</option>
                    <option value="RO">RO</option>
                    <option value="RR">RR</option>
                    <option value="SC">SC</option>
                    <option value="SP">SP</option>
                    <option value="SE">SE</option>
                    <option value="TO">TO</option>
                </select>
            </div>
        </div>
        <button type="button" onclick="adicionarEnderecoEntrega()">Adicionar Outro Endereço de Entrega</button>

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required>

        <button type="submit">Cadastrar</button>
    </form>

    <script>
        function adicionarEnderecoEntrega() {
            const container = document.getElementById('enderecosEntrega');
            const novoEndereco = container.querySelector('.enderecoEntrega').cloneNode(true);
            container.appendChild(novoEndereco);
        }
    </script>
</body>
</html>
