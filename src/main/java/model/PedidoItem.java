package model;

public class PedidoItem {
    private int id;
    private int pedidoId;
    private int tenisId;
    private int quantidade;
    private double precoUnitario;

    public PedidoItem(int pedidoId, int tenisId, int quantidade, double preco) {
        this.pedidoId = pedidoId;
        this.tenisId = tenisId;
        this.quantidade = quantidade;
        this.precoUnitario = preco;
    }

    public PedidoItem() {

    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getTenisId() {
        return tenisId;
    }

    public void setTenisId(int tenisId) {
        this.tenisId = tenisId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
