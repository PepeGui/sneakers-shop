package model;

import java.util.List;

public class Cliente {
    private int id;
    private String nome;
    private String dataNascimento;
    private String genero;
    private String cpf;
    private String email;
    private String senha; // A senha será armazenada encriptada.
    private String chaveAES;
    private List<Endereco> enderecosEntrega;

    // Construtor padrão
    public Cliente() {}

    public int getId() {
        return id;
    }

    // Construtor completo
    public Cliente(int id, String nome, String dataNascimento, String genero, String cpf, String email, String senha, List<Endereco> enderecosEntrega) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.enderecosEntrega = enderecosEntrega;
    }

    // Getters e Setters para os campos

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getChaveAES() {
        return chaveAES;
    }

    public void setChaveAES(String chaveAES) {
        this.chaveAES = chaveAES;
    }

    public List<Endereco> getEnderecosEntrega() {
        return enderecosEntrega;
    }

    public void setEnderecosEntrega(List<Endereco> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento='" + dataNascimento + '\'' +
                ", genero='" + genero + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
