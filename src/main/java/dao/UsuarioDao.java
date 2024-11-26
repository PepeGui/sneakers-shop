package dao;

import model.Usuario;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.print.DocFlavor.STRING;

import controller.Criptografia;

public class UsuarioDao {
    private static final String SQL_Select = "SELECT * FROM USUARIO";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    public static void createUsuario(Usuario pUser){

        String SQL = "insert INTO usuario (nome, email, senha, cpf, grupo, ativo,chave_aes) VALUES (?,?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            System.out.println("success in database connection insert usuario");

            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            System.out.println(pUser.getNome()+"/"+pUser.getEmail()+"/"+pUser.getSenha()+"/"+pUser.getCpf()+"/"+pUser.getGrupo()+"/"+pUser.getAtivo());

            preparedStatement.setString(1, pUser.getNome());
            preparedStatement.setString(2,pUser.getEmail());
            preparedStatement.setString(3, pUser.getSenha());
            preparedStatement.setLong(4, pUser.getCpf());
            preparedStatement.setString(5, pUser.getGrupo());
            preparedStatement.setBoolean(6,true);
            preparedStatement.setString(7, pUser.getChaveAES());

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

    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = null;

        String sql = "SELECT * FROM USUARIO WHERE EMAIL = ?";

        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Cria o objeto Usuario com os dados do banco
                usuario = new Usuario();
                usuario.setId(rs.getInt("ID"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setNome(rs.getString("NOME"));
                usuario.setSenha(rs.getString("SENHA"));
                usuario.setGrupo(rs.getString("GRUPO"));  // Incluindo o grupo (Administrador/Estoquista)
                // Preencha outros atributos do usuário conforme necessário
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario; // Retorna o objeto Usuario, ou null se não encontrado
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
    public Usuario buscaUsuariosPorID(Usuario pUsuario) {
        List<Usuario> usuarios = new ArrayList<>();
        String SQL = "SELECT * FROM USUARIO WHERE ID = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("success in database connection buscaUsuariosPorID");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, pUsuario.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String grupo = resultSet.getString("grupo");
                String ativo = resultSet.getString("ativo");
                String chaveAES = resultSet.getString("chave_aes");
                usuarios.add(new Usuario(Integer.parseInt(id), nome, email, senha, Long.parseLong(cpf), grupo, Boolean.parseBoolean(ativo),chaveAES));
            }
            System.out.println("success in buscaUsuariosPorID");

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return usuarios.get(0);
    }

    public boolean verificarLogin(Usuario u) {
        String SQL = "SELECT * FROM usuario WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, u.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Usuario> usuarios = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String cpf = resultSet.getString("cpf");
                String grupo = resultSet.getString("grupo");
                String ativo = resultSet.getString("ativo");
                String chaveAES = resultSet.getString("chave_aes");
                usuarios.add(new Usuario(Integer.parseInt(id), nome, email, senha, Long.parseLong(cpf), grupo, Boolean.parseBoolean(ativo),chaveAES));
            }
            Criptografia crip = new Criptografia();
            for(Usuario usuario : usuarios){
                SecretKey chaveAES = crip.converterStringParaChave(usuario.getChaveAES(), "AES");
                String senhaDescriptografada = crip.descriptografar(usuario.getSenha(), chaveAES);
                if(u.getSenha().equals(senhaDescriptografada) && usuario.getAtivo() == true)
                    return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return false; // Usuário não encontrado, login falhou
    }
    public boolean  alterarUsuario(Usuario pUser) {

        String SQL = "UPDATE USUARIO SET nome = ?, email = ?, senha = ?, cpf = ?, grupo = ?, chave_aes = ? WHERE ID = ?";
        boolean sucesso = false;

        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            System.out.println("Conexão bem-sucedida com o banco de dados Alterar Usuario");

            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            preparedStatement.setString(1, pUser.getNome());
            preparedStatement.setString(2,pUser.getEmail());
            preparedStatement.setString(3, pUser.getSenha());
            preparedStatement.setLong(4, pUser.getCpf());
            preparedStatement.setString(5, pUser.getGrupo());
            preparedStatement.setString(6, pUser.getChaveAES());
            preparedStatement.setInt(7,pUser.getId());


            int rowsAffected = preparedStatement.executeUpdate();
            sucesso = (rowsAffected > 0);

            System.out.println("Dados do usuário atualizados com sucesso!");

            con.close();

        } catch (Exception e) {
            System.out.println("Falha na conexão com o banco de dados Alterar Usuario");
            e.printStackTrace();
        }
        return sucesso;
    }

    public boolean alterarStatus(Usuario usuario) {
        String SQL = "UPDATE USUARIO SET ATIVO = ? WHERE ID = ?";
        boolean sucesso = false;

        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

            preparedStatement.setBoolean(1, usuario.getAtivo());  // Usa o status booleano do objeto
            preparedStatement.setInt(2, usuario.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            sucesso = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace(); // Log para depuração
        }

        return sucesso;
    }


}

    
