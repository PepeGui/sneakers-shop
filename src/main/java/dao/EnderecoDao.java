package dao;

import model.Endereco;
import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDao {

    private static final String DB_URL = "jdbc:h2:~/test";  // URL para o H2
    private static final String DB_USERNAME = "sa";  // Usuário do H2
    private static final String DB_PASSWORD = "sa";  // Senha do H2

    public int salvarEndereco(Endereco endereco) throws Exception {
        String sql = "INSERT INTO endereco (cep, logradouro, numero, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getLogradouro());
            stmt.setString(3, endereco.getNumero());
            stmt.setString(4, endereco.getBairro());
            stmt.setString(5, endereco.getCidade());
            stmt.setString(6, endereco.getUf());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // Retorna o ID gerado do endereço
            } else {
                throw new Exception("Falha ao obter ID do endereço.");
            }
        }
    }

    public List<Endereco> buscarEnderecosPorClienteId(int clienteId) throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM endereco WHERE cliente_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("id"));
                endereco.setCep(rs.getString("cep"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getString("numero"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setUf(rs.getString("uf"));
                enderecos.add(endereco);
            }
        }
        return enderecos;
    }
    public Endereco buscaEnderecoPorID(int pId) {
        List<Endereco> enderecos = new ArrayList<>();
        String SQL = "SELECT * FROM endereco WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("success in database connection buscaEnderecoPorID");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1,pId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(resultSet.getInt("id"));
                endereco.setCep(resultSet.getString("cep"));
                endereco.setLogradouro(resultSet.getString("logradouro"));
                endereco.setNumero(resultSet.getString("numero"));
                endereco.setBairro(resultSet.getString("bairro"));
                endereco.setCidade(resultSet.getString("cidade"));
                endereco.setUf(resultSet.getString("uf"));
                enderecos.add(endereco);
            }
            System.out.println("success in buscaEnderecoPorID");

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return enderecos.get(0);
    }
}
