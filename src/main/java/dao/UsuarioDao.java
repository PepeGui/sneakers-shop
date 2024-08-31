package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UsuarioDao {
    public static void createItemPedido(Usuario pUser){

        String SQL = "INSERT INTO usuario (nome, email, senha, cpf, grupo, ativo) VALUES (?,?,?,?,?,?)";

        try {
            Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa","sa");

            System.out.println("success in database connection");

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
}
