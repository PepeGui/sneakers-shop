package model;

public class ItemCarrinho {
    private Tenis tenis;
    private int quantidade;
    private Cliente cliente;

    public ItemCarrinho(Tenis tenis, int quantidade, Cliente cliente) {
        this.tenis = tenis;
        this.quantidade = quantidade;
        this.cliente = cliente;
    }

    public ItemCarrinho(Tenis tenis, int quantidade) {
        this.tenis = tenis;
        this.quantidade = quantidade;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tenis getTenis() {
        return tenis;
    }

    public void setTenis(Tenis tenis) {
        this.tenis = tenis;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Calcula o total do item no carrinho
    public double getTotal() {
        return tenis.getPreco() * quantidade;
    }
}

