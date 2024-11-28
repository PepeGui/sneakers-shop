<!doctype html>
<html lang="pt-BR">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Cliente, dao.ClienteDao" %>
<%
    // Recupera o cliente pelo ID da sessão ou de uma consulta
    ClienteDao clienteDao = new ClienteDao();
    Cliente cliente = clienteDao.getClienteById((Integer) session.getAttribute("clienteId"));

    if (cliente == null) {
        response.sendRedirect("/login-cliente.jsp");
        return;
    }
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alterar Cliente - Sneaker-Shop</title>
    <link rel="stylesheet" href="alterar-cliente.css">
</head>
<body>
    <h2>Alteração de Dados do Cliente</h2>
    <form action="/alterar-cliente" method="POST">

        <!-- Campo para Nome Completo -->
        <label for="nome">Nome Completo:</label>
        <input type="text" id="nome" name="nome" value="<%= cliente.getNome() %>" required pattern="[A-Za-z]{3,} [A-Za-z]{3,}"
               title="O nome deve conter pelo menos duas palavras com 3 letras cada.">

        <!-- Campo para Data de Nascimento -->
        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" id="dataNascimento" name="dataNascimento" value="<%= cliente.getDataNascimento() %>" required>

        <!-- Campo para Gênero -->
        <label for="genero">Gênero:</label>
        <select id="genero" name="genero" required>
            <option value="masculino" <%= "masculino".equals(cliente.getGenero()) ? "selected" : "" %>>Masculino</option>
            <option value="feminino" <%= "feminino".equals(cliente.getGenero()) ? "selected" : "" %>>Feminino</option>
            <option value="outro" <%= "outro".equals(cliente.getGenero()) ? "selected" : "" %>>Outro</option>
        </select>

        <!-- Campo para CPF (não editável) -->
        <label for="cpf">CPF:</label>
        <input type="text" id="cpf" name="cpf" value="<%= cliente.getCpf() %>" readonly>

        <!-- Campo para Email -->
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= cliente.getEmail() %>" required>

        <!-- Endereço de Entrega -->
        <h3>Endereço de Entrega</h3>
        <div id="enderecosEntrega">
            <%
                for (int i = 0; i < cliente.getEnderecosEntrega().size(); i++) {
                    model.Endereco endereco = cliente.getEnderecosEntrega().get(i);
            %>
                <div class="enderecoEntrega">
                    <label for="cepEntrega-<%= i %>">CEP:</label>
                    <input type="text" id="cepEntrega-<%= i %>" name="cepEntrega[]" value="<%= endereco.getCep() %>" required
                           pattern="\\d{5}-\\d{3}" title="Digite um CEP válido (formato 00000-000).">

                    <label for="logradouroEntrega-<%= i %>">Logradouro:</label>
                    <input type="text" id="logradouroEntrega-<%= i %>" name="logradouroEntrega[]" value="<%= endereco.getLogradouro() %>" required>

                    <label for="numeroEntrega-<%= i %>">Número:</label>
                    <input type="text" id="numeroEntrega-<%= i %>" name="numeroEntrega[]" value="<%= endereco.getNumero() %>" required>

                    <label for="bairroEntrega-<%= i %>">Bairro:</label>
                    <input type="text" id="bairroEntrega-<%= i %>" name="bairroEntrega[]" value="<%= endereco.getBairro() %>" required>

                    <label for="cidadeEntrega-<%= i %>">Cidade:</label>
                    <input type="text" id="cidadeEntrega-<%= i %>" name="cidadeEntrega[]" value="<%= endereco.getCidade() %>" required>

                    <label for="ufEntrega-<%= i %>">UF:</label>
                    <select id="ufEntrega-<%= i %>" name="ufEntrega[]" required>
                        <option value="AC" <%= "AC".equals(endereco.getUf()) ? "selected" : "" %>>AC</option>
                        <option value="SP" <%= "SP".equals(endereco.getUf()) ? "selected" : "" %>>SP</option>
                        <!-- Adicione as demais UFs aqui -->
                    </select>
                </div>
            <% } %>
        </div>
        <button type="button" onclick="adicionarEnderecoEntrega()">Adicionar Outro Endereço de Entrega</button>

        <!-- Campo para Senha -->
        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" value="<%= cliente.getSenha() %>" required>

        <!-- Botão para Salvar -->
        <button type="submit">Salvar Alterações</button>
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
