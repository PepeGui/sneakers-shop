<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Checkout</title>
    <link rel="stylesheet" href="/Checkout/checkout.css">
</head>
<body>
    <h2>Escolha uma forma de pagamento</h2>

    <form action="/processarPagamento" method="post">
        <!-- Escolha da forma de pagamento -->
        <label>
            <input type="radio" name="formaPagamento" value="boleto" required>
            Boleto
        </label>

        <label>
            <input type="radio" name="formaPagamento" value="cartao" required>
            Cartão de Crédito
        </label>

        <!-- Detalhes do cartão de crédito (exibidos apenas se cartão for selecionado) -->
        <div id="cartaoSection" style="display: none;">
            <label for="numeroCartao">Número do Cartão:</label>
            <input type="text" name="numeroCartao" id="numeroCartao" maxlength="16">

            <label for="codigoVerificador">Código Verificador:</label>
            <input type="text" name="codigoVerificador" id="codigoVerificador" maxlength="3">

            <label for="nomeCompleto">Nome Completo:</label>
            <input type="text" name="nomeCompleto" id="nomeCompleto">

            <label for="dataVencimento">Data de Vencimento:</label>
            <input type="month" name="dataVencimento" id="dataVencimento">

            <label for="parcelas">Quantidade de Parcelas:</label>
            <select name="parcelas" id="parcelas">
                <option value="1">1x</option>
                <option value="2">2x</option>
                <option value="3">3x</option>
                <option value="4">4x</option>
                <option value="5">5x</option>
                <option value="6">6x</option>
            </select>
        </div>

        <button type="submit">Continuar para Validar Pedido</button>
    </form>

    <script>
        // JavaScript para exibir os campos do cartão apenas se "cartão" for selecionado
        document.querySelectorAll('input[name="formaPagamento"]').forEach((elem) => {
            elem.addEventListener("change", function() {
                document.getElementById("cartaoSection").style.display = this.value === "cartao" ? "block" : "none";
            });
        });
    </script>
</body>
</html>
