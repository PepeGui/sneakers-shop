package dao;

import model.ItemCarrinho;
import model.Tenis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoDao {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    // Método para adicionar um ItemCarrinho
    public boolean adicionarItemAoCarrinho(ItemCarrinho itemCarrinho) {
        return adicionarItemAoCarrinho(itemCarrinho.getCliente().getId() , itemCarrinho.getTenis().getId(), itemCarrinho.getQuantidade());
    }

    // Método para adicionar um item ao carrinho usando ID e quantidade
    public boolean adicionarItemAoCarrinho(int clienteId, int tenisId, int quantidade) {
        String insertSql = "INSERT INTO Carrinho (cliente_id, tenis_id, quantidade) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Carrinho SET quantidade = quantidade + ? WHERE cliente_id = ? AND tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement insertStmt = con.prepareStatement(insertSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            // Tente inserir o item no carrinho
            insertStmt.setInt(1, clienteId);
            insertStmt.setInt(2, tenisId);
            insertStmt.setInt(3, quantidade);

            try {
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                // Se a inserção falhar por duplicação, atualize a quantidade
                if (e.getErrorCode() == 23505) { // Código de erro para duplicação de chave no H2
                    updateStmt.setInt(1, quantidade);
                    updateStmt.setInt(2, clienteId);
                    updateStmt.setInt(3, tenisId);
                    updateStmt.executeUpdate(); // Atualize a quantidade
                } else {
                    throw e; // Se não for um erro de duplicação, relance a exceção
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Método para obter os itens do carrinho
    public List<ItemCarrinho> obterItensCarrinho() {
        List<ItemCarrinho> itensCarrinho = new ArrayList<>();
        String sql = "SELECT * FROM Carrinho"; // Altere conforme necessário

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Crie um ItemCarrinho a partir do ResultSet
                int tenisId = rs.getInt("tenis_id");
                int quantidade = rs.getInt("quantidade");
                // Presuma que você tenha um método para obter o tênis pelo ID
                Tenis tenis = new TenisDao().getTenisById(tenisId);
                itensCarrinho.add(new ItemCarrinho(tenis, quantidade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itensCarrinho;
    }
}
