<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneaker-Shop - Escolher Pagamento</title>
    <link rel="stylesheet" href="/Escolher-Pagamento/escolher-pagamento.css">
</head>
<body>
    <h2>Escolher Forma de Pagamento</h2>

    <form id="form-pagamento" action="/resumo-pedido" method="POST">
        <label>
            <input type="radio" name="formaPagamento" value="boleto">
            Boleto
        </label>
        <br>
        <label>
            <input type="radio" name="formaPagamento" value="cartao">
            Cartão de Crédito
        </label>

        <div id="cartao-info">
            <h3>Informações do Cartão</h3>
            <label>Número do Cartão: <input type="text" name="numeroCartao"></label><br>
            <label>Nome Completo: <input type="text" name="nomeCartao"></label><br>
            <label>Data de Vencimento: <input type="month" name="validadeCartao"></label><br>
            <label>Código Verificador: <input type="text" name="cvv"></label><br>
            <label>Parcelas:
                <select name="parcelas">
                    <option value="">Selecione</option>
                    <option value="1">1x</option>
                    <option value="2">2x</option>
                    <option value="3">3x</option>
                    <option value="4">4x</option>
                </select>
            </label>
        </div>

        <button type="submit">Validar Pagamento</button>
        <p class="erro" id="erro-msg"></p>
    </form>

    <script>
        const radiosPagamento = document.querySelectorAll('input[name="formaPagamento"]');
        const cartaoInfo = document.getElementById("cartao-info");
        const form = document.getElementById("form-pagamento");
        const erroMsg = document.getElementById("erro-msg");

        // Mostra/esconde os campos do cartão conforme a seleção
        radiosPagamento.forEach(radio => {
            radio.addEventListener("change", () => {
                if (radio.value === "cartao") {
                    cartaoInfo.style.display = "block";
                } else {
                    cartaoInfo.style.display = "none";
                }
                erroMsg.textContent = ""; // Limpa mensagem de erro ao mudar a seleção
            });
        });

        // Validação no envio do formulário
        form.addEventListener("submit", (event) => {
            const formaPagamento = document.querySelector('input[name="formaPagamento"]:checked');

            if (!formaPagamento) {
                erroMsg.textContent = "Por favor, escolha uma forma de pagamento.";
                event.preventDefault();
                return;
            }

            if (formaPagamento.value === "cartao") {
                const numeroCartao = document.querySelector('input[name="numeroCartao"]').value.trim();
                const nomeCartao = document.querySelector('input[name="nomeCartao"]').value.trim();
                const validadeCartao = document.querySelector('input[name="validadeCartao"]').value.trim();
                const cvv = document.querySelector('input[name="cvv"]').value.trim();
                const parcelas = document.querySelector('select[name="parcelas"]').value.trim();

                if (!numeroCartao || !nomeCartao || !validadeCartao || !cvv || !parcelas) {
                    erroMsg.textContent = "Preencha todos os campos do cartão.";
                    event.preventDefault();
                }
            }
        });
    </script>
</body>
</html>
