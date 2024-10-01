package dao;

import model.ImagemTenis;
import model.Tenis;
import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TenisDao {

    private static final String SQL_Select = "SELECT * FROM TENIS";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    public boolean createTenis(Tenis tenis) {
        String sqlProduto = "INSERT INTO TENIS (nome, avaliacao, descricao, preco, estoque) VALUES (?, ?, ?, ?, ?)";
        String sqlImagem = "INSERT INTO IMAGEMTENIS (tenis_id, caminho, principal) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement psProduto = con.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)) {

            psProduto.setString(1, tenis.getNome());
            psProduto.setDouble(2, tenis.getAvaliacao());
            psProduto.setString(3, tenis.getDescricao());
            psProduto.setDouble(4, tenis.getPreco());
            psProduto.setInt(5, tenis.getEstoque());

            psProduto.executeUpdate();

            ResultSet rs = psProduto.getGeneratedKeys();
            if (rs.next()) {
                int tenisId = rs.getInt(1);

                for (ImagemTenis imagem : tenis.getImagens()) {
                    try (PreparedStatement psImagem = con.prepareStatement(sqlImagem)) {
                        psImagem.setInt(1, tenisId);
                        psImagem.setString(2, imagem.getCaminho());
                        psImagem.setBoolean(3, imagem.isPrincipal());
                        psImagem.executeUpdate();
                    }
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterarStatus(Tenis t) {
        String SQL = "UPDATE TENIS SET ATIVO = ? WHERE ID = ?";
        boolean sucesso = false;

        try (Connection con = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = con.prepareStatement(SQL)) {

            preparedStatement.setBoolean(1, t.isAtivo());  // Usa o status booleano do objeto
            preparedStatement.setInt(2, t.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            sucesso = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace(); // Log para depuração
        }

        return sucesso;
    }

    public List<Tenis> getAllTenis() {
        List<Tenis> tenisList = new ArrayList<>();
        String SQL_SELECT_ALL = "SELECT * FROM TENIS";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_Select);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Tenis tenis = new Tenis();
                tenis.setId(resultSet.getInt("ID"));
                tenis.setNome(resultSet.getString("NOME"));
                tenis.setDescricao(resultSet.getString("DESCRICAO"));
                tenis.setPreco(resultSet.getDouble("PRECO"));
                tenis.setEstoque(resultSet.getInt("ESTOQUE"));
                tenis.setAtivo(resultSet.getBoolean("ATIVO"));
                tenisList.add(tenis);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenisList;
    }

    public List<Tenis> buscarTenisPorNome(String nome) {
        List<Tenis> tenisList = new ArrayList<>();
        String SQL_SELECT_BY_NAME = "SELECT * FROM TENIS WHERE NOME LIKE ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_NAME)) {

            preparedStatement.setString(1, "%" + nome + "%"); // Wildcard para busca
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Tenis tenis = new Tenis();
                    tenis.setId(resultSet.getInt("ID"));
                    tenis.setNome(resultSet.getString("NOME"));
                    tenis.setDescricao(resultSet.getString("DESCRICAO"));
                    tenis.setPreco(resultSet.getDouble("PRECO"));
                    tenis.setEstoque(resultSet.getInt("ESTOQUE"));
                    tenis.setAtivo(resultSet.getBoolean("ATIVO"));
                    tenisList.add(tenis);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenisList;
    }

}
