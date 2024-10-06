<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sneaker-Shop - Carrinho</title>
    <link rel="stylesheet" href="/Carrinho/carrinho.css">
</head>
<body>
<header>
    <h1>Sneaker-Shop</h1>
    <nav>
        <ul>
            <li><a href="index.html">Home</a></li>
            <li><a href="produtos.html">Produtos</a></li>
            <li><a href="carrinho.html">Carrinho</a></li>
        </ul>
    </nav>
</header>

<main>
    <h2>Carrinho de Compras</h2>
    <table class="cart-table">
        <thead>
        <tr>
            <th>Produto</th>
            <th>Quantidade</th>
            <th>Preço</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Tênis Nike Air Max</td>
            <td><input type="number" value="1" min="1"></td>
            <td>R$ 499,90</td>
            <td>R$ 499,90</td>
        </tr>
        <tr>
            <td>Tênis Adidas Ultraboost</td>
            <td><input type="number" value="1" min="1"></td>
            <td>R$ 599,90</td>
            <td>R$ 599,90</td>
        </tr>
        </tbody>
    </table>

    <div class="cart-summary">
        <p>Subtotal: <span>R$ 1.099,80</span></p>
        <p>Frete: <span>R$ 30,00</span></p>
        <p>Total: <strong>R$ 1.129,80</strong></p>
        <button class="checkout-btn">Finalizar Compra</button>
    </div>
</main>

<footer>
    <p>&copy; 2024 Sneaker-Shop. Todos os direitos reservados.</p>
</footer>
</body>
</html>
