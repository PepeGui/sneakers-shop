package model;

import java.util.List;

public class Tenis {
    private int id;
    private String nome;
    private double preco;
    private int estoque;
    private String descricao;
    private double avaliacao;
    private List<ImagemTenis> imagens; // Lista de imagens associadas ao tênis
    private boolean ativo;

    public Tenis(){

    }

    public Tenis(int id){
        this.id = id;
    }

    public Tenis(String nome, double preco, int estoque, String descricao, double avaliacao, boolean ativo) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.descricao = descricao;
        this.avaliacao = avaliacao;
        this.ativo = ativo;
    }
    // Getters e Setters para os campos, incluindo a lista de imagens
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<ImagemTenis> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemTenis> imagens) {
        this.imagens = imagens;
    }
}

