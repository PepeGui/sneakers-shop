package dao;

import controller.Criptografia;
import model.Cliente;

import javax.crypto.SecretKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";
    public boolean cadastrarCliente(Cliente cliente) throws Exception {
        String sql = "INSERT INTO cliente (nome, dataNascimento, genero, cpf, email, senha, chaveAES) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getDataNascimento());
            stmt.setString(3, cliente.getGenero());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getEmail());
            stmt.setString(6, cliente.getSenha());
            stmt.setString(7, cliente.getChaveAES());
            stmt.execute();
            con.close();

            return true;

        }catch (Exception e){
                System.out.println("fail in database connection insert usuario");
                e.getMessage();
                e.getStackTrace();
                return false;
        }
    }
    public boolean verificarLogin(Cliente c) {
        String SQL = "SELECT * FROM cliente WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, c.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String chaveAES = resultSet.getString("chaveaes");
                clientes.add(new Cliente(Integer.parseInt(id), nome, email, senha, (cpf),chaveAES));
            }
            Criptografia crip = new Criptografia();
            for(Cliente usuario : clientes){
                SecretKey chaveAES = crip.converterStringParaChave(usuario.getChaveAES(), "AES");
                String senhaDescriptografada = crip.descriptografar(usuario.getSenha(), chaveAES);
                if(c.getSenha().equals(senhaDescriptografada))
                    return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return false;
    }
    public Cliente buscarClientePorEmail(String email) {
        Cliente cliente = null;

        String sql = "SELECT * FROM cliente WHERE EMAIL = ?";

        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("ID"));
                cliente.setEmail(rs.getString("EMAIL"));
                cliente.setSenha(rs.getString("SENHA"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
}

    // Feche a conexão quando não for mais necessária

