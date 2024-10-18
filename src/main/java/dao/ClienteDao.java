package dao;

import model.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            con.close();
            return stmt.executeUpdate() > 0;

        }catch (Exception e){
                System.out.println("fail in database connection insert usuario");
                e.getMessage();
                e.getStackTrace();
                return false;
        }
    }
    }

    // Feche a conexão quando não for mais necessária

