package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioDao {
    public void createUsuario(Usuario pUser){

        String SQL = "INSERT INTO usuario (nome, email, senha, cpf, grupo, ativo) VALUES (?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            System.out.println("success in database connection insert usuario");

            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            preparedStatement.setString(1, pUser.getNome());
            preparedStatement.setString(2,pUser.getEmail());
            preparedStatement.setString(3, pUser.getSenha());
            preparedStatement.setLong(4, pUser.getCpf());
            preparedStatement.setString(5, pUser.getGrupo());
            preparedStatement.setBoolean(6,pUser.isAtivo());

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
    public Boolean VerificacaoEmail(Usuario pUser) {

        String SQL = "SELECT * FROM USUARIO WHERE EMAIL = ?";

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
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
                return true;
            }

            System.out.println("usuario não cadastrado");
            connection.close();
            return false;

        } catch (Exception e) {

            System.out.println("fail in database connection verificação de Email");

            return false;

        }
    }
}
