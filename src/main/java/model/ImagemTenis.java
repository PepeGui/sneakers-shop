package model;

public class ImagemTenis {
    private int id;
    private int produtoId;  // Referência ao produto
    private String caminho; // Caminho da imagem no sistema
    private boolean principal; // Se é a imagem principal

    // Construtores, Getters e Setters
    public ImagemTenis() {
    }

    public ImagemTenis(int id, int produtoId, String caminho, boolean principal) {
        this.id = id;
        this.produtoId = produtoId;
        this.caminho = caminho;
        this.principal = principal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
}

