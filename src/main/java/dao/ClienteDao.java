package dao;

import model.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDao {
    private Connection connection;

    public ClienteDao() {
        try {
            // Substitua os parâmetros abaixo pelos seus detalhes do banco de dados
            String url = "jdbc:h2:~/test"; // URL do seu banco de dados
            String usuario = "sa"; // Usuário do banco de dados
            String senha = ""; // Senha do banco de dados
            connection = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public boolean cadastrarCliente(Cliente cliente) throws Exception {
        String sql = "INSERT INTO clientes (nome, dataNascimento, genero, cpf, email, senha, chaveAES) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getDataNascimento());
            stmt.setString(3, cliente.getGenero());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getSenha());
            stmt.setString(7, cliente.getChaveAES());
            return stmt.executeUpdate() > 0; // Retorna verdadeiro se um registro foi inserido
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Erro ao cadastrar cliente", e);
        }
    }

    // Feche a conexão quando não for mais necessária
    public void fecharConexao() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
