<!doctype html>
<html lang="pt-BR">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.Tenis, dao.TenisDao" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro - Sneaker-Shop</title>
    <link rel="stylesheet" href="cadastro-cliente.css">
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
    <script src="buscarEndereco.js"></script>
</head>
<body>
    <h2>Cadastro de Cliente</h2>
    <form action="/cadastro-cliente" method="POST">

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

        <h3>Endereço de Cobrança</h3>
        <div id="enderecosCobrança">
            <div class="enderecoCobrança">
                <label for="cep">CEP:</label>
                               <input type="text" id="cep" name="cep" placeholder="Digite o CEP" required onblur="buscarEndereco()">
                               <br>
                               <label for="logradouro">Logradouro:</label>
                               <input type="text" id="logradouro" name="logradouro" placeholder="Logradouro">
                               <br>
                               <label for="numero">Número:</label>
                               <input type="text" id="numero" name="numero" placeholder="Digite o número" required>
                               <br>
                               <label for="complemento">Complemento:</label>
                               <input type="text" id="complemento" name="complemento" placeholder="Complemento">
                               <br>
                               <label for="bairro">Bairro:</label>
                               <input type="text" id="bairro" name="bairro" placeholder="Bairro">
                               <br>
                               <label for="cidade">Cidade:</label>
                               <input type="text" id="cidade" name="cidade" placeholder="Cidade">
                               <br>
                               <label for="uf">UF:</label>
                               <input type="text" id="uf" name="uf" placeholder="UF">
            </div>
        </div>


        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required>

        <button type="submit">Cadastrar</button>
    </form>

    <script>
        function adicionarEnderecoCobrança() {
            const container = document.getElementById('enderecosCobrança');
            const novoEndereco = container.querySelector('.enderecoCobrança').cloneNode(true);
            container.appendChild(novoEndereco);
        }
    </script>
</body>
</html>
