package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private static final String SQL_Select = "SELECT * FROM USUARIO";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    public static void createUsuario(Usuario pUser){

        String SQL = "insert INTO usuario (nome, email, senha, cpf, grupo, ativo) VALUES (?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            System.out.println("success in database connection insert usuario");

            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            System.out.println(pUser.getNome()+"/"+pUser.getEmail()+"/"+pUser.getSenha()+"/"+pUser.getCpf()+"/"+pUser.getGrupo()+"/"+pUser.isAtivo());

            preparedStatement.setString(1, pUser.getNome());
            preparedStatement.setString(2,pUser.getEmail());
            preparedStatement.setString(3, pUser.getSenha());
            preparedStatement.setLong(4, pUser.getCpf());
            preparedStatement.setString(5, pUser.getGrupo());
            preparedStatement.setBoolean(6,true);

            System.out.println("passou aqui 2");
            preparedStatement.execute();

            System.out.println("success in insert usuario");

            con.close();

        }
        catch (Exception e){
            System.out.println("fail in database connection insert usuario");
            e.getMessage();
            e.getStackTrace();
        }
    }

    public static void updateUser(Usuario user, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            System.out.println("As senhas não coincidem!");
            return;
        }

        // Encripta a senha


        // Comando SQL para atualizar o nome, CPF e senha, mas não o email
        String SQL = "UPDATE USERS SET NOME = ?, CPF = ?, SENHA = ? WHERE ID = ?";

        try {
            // Conexão com o banco de dados
            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            System.out.println("Conexão bem-sucedida com o banco de dados");

            // Prepara a instrução SQL
            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            // Define os parâmetros da query
            preparedStatement.setString(1, user.getNome());
            //preparedStatement.setString(2, user.getCpf());
            //preparedStatement.setString(3, hashedPassword);
            preparedStatement.setInt(4, user.getId());

            // Executa a query de atualização
            preparedStatement.executeUpdate();

            System.out.println("Dados do usuário atualizados com sucesso!");

            // Fecha a conexão
            con.close();
        } catch (Exception e) {
            System.out.println("Falha na conexão com o banco de dados");
            e.printStackTrace();
        }
    }

    public Boolean VerificacaoEmail(Usuario pUser) {

        String SQL = "SELECT * FROM USUARIO WHERE EMAIL = ?";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("success in database connection verificação de Email");

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, pUser.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Usuario> users = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("ID");

                Usuario user = new Usuario();
                user.setId(Integer.parseInt(id));

                users.add(user);
            }
            if (users.size() > 0) {
                System.out.println("usuario já cadastrado");
                connection.close();
                return false;
            }

            System.out.println("usuario não cadastrado");
            connection.close();
            return true;

        } catch (Exception e) {

            System.out.println("fail in database connection verificação de Email");

            return false;

        }
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_Select);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String grupo = resultSet.getString("grupo");
                String ativo = resultSet.getString("ativo");
                usuarios.add(new Usuario(Integer.parseInt(id), nome, email, senha, Long.parseLong(cpf), grupo, Boolean.parseBoolean(ativo)));
            }

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return usuarios;
    }
    public List<Usuario> buscaUsuariosPorNome(String pNome) {
        List<Usuario> usuarios = new ArrayList<>();
        String SQL = "SELECT * FROM USUARIO WHERE NOME = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);

             preparedStatement.setString(1, pNome);

             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String grupo = resultSet.getString("grupo");
                String ativo = resultSet.getString("ativo");
                usuarios.add(new Usuario(Integer.parseInt(id), nome, email, senha, Long.parseLong(cpf), grupo, Boolean.parseBoolean(ativo)));
            }

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return usuarios;
    }

    public boolean verificarLogin(Usuario u) {
        String SQL = "SELECT COUNT(*) FROM usuario WHERE email = ? AND senha = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, u.getEmail());
            preparedStatement.setString(2, u.getSenha());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true; // Usuário encontrado, login bem-sucedido
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return false; // Usuário não encontrado, login falhou
    }

}
