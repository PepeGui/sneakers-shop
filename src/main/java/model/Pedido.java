package model;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private int clienteId;
    private Date dataPedido;
    private String status;
    private double valorTotal;
    private int enderecoEntregaId;
    private List<PedidoItem> itens;

    public Pedido(Integer clienteId, int id) {
        this.id = id;
        this.clienteId = clienteId;
    }

    public Pedido(int clienteId, int enderecoEntregaId, double valorTotal, String status, List<PedidoItem> itens) {
        this.clienteId = clienteId;
        this.enderecoEntregaId = enderecoEntregaId;
        this.valorTotal = valorTotal;
        this.status = status;
        this.itens = itens;
    }

    public Pedido() {

    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getEnderecoEntregaId() {
        return enderecoEntregaId;
    }

    public void setEnderecoEntregaId(int enderecoEntregaId) {
        this.enderecoEntregaId = enderecoEntregaId;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoItem> itens) {
        this.itens = itens;
    }
}
