package model;

import java.util.List;

public class Tenis {
    private int id;
    private String nome;
    private double avaliacao;  // De 1 a 5, incrementos de 0.5
    private String descricao;
    private double preco;
    private int estoque;
    private List<ImagemTenis> imagens;  // Lista de imagens associadas ao produto
    private boolean ativo;

    // Construtores, Getters e Setters
    public Tenis() {
    }

    public Tenis(int id, String nome, double avaliacao, String descricao, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;

    }

    public Tenis(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public List<ImagemTenis> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemTenis> imagens) {
        this.imagens = imagens;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
