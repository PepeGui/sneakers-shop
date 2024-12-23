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
        return adicionarItemAoCarrinho(itemCarrinho.getTenis().getId(), itemCarrinho.getQuantidade(), itemCarrinho.getCliente().getId());
    }

    // Método para adicionar um item ao carrinho usando ID e quantidade (sem clienteId)
    public boolean adicionarItemAoCarrinho(int clienteId, int tenisId, int quantidade) {
        String insertSql = "INSERT INTO Carrinho (cliente_id, tenis_id, quantidade) VALUES (?, ?, ?)";
        String updateSql = "UPDATE Carrinho SET quantidade = quantidade + ? WHERE cliente_id = ? AND tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement insertStmt = con.prepareStatement(insertSql);
             PreparedStatement updateStmt = con.prepareStatement(updateSql)) {

            // Configura o SQL de inserção
            insertStmt.setInt(1, clienteId);
            insertStmt.setInt(2, tenisId);
            insertStmt.setInt(3, quantidade);

            try {
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                if (e.getErrorCode() == 23505) { // Código de erro para duplicação de chave no H2
                    // Configura o SQL de atualização
                    updateStmt.setInt(1, quantidade);
                    updateStmt.setInt(2, clienteId);
                    updateStmt.setInt(3, tenisId);
                    updateStmt.executeUpdate(); // Atualiza a quantidade
                } else {
                    throw e;
                }
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removerItemDoCarrinho(int clienteId, int tenisId) {
        String deleteSql = "DELETE FROM Carrinho WHERE cliente_id = ? AND tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {

            // Definindo os parâmetros na consulta SQL
            deleteStmt.setInt(1, clienteId);
            deleteStmt.setInt(2, tenisId);

            // Executa a remoção
            int rowsAffected = deleteStmt.executeUpdate();

            // Verifica se o item foi removido
            if (rowsAffected > 0) {
                System.out.println("Item removido com sucesso para clienteId: " + clienteId + " e tenisId: " + tenisId);
                return true;
            } else {
                System.out.println("Nenhum item removido para clienteId: " + clienteId + " e tenisId: " + tenisId);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao remover item do carrinho: clienteId = " + clienteId + ", tenisId = " + tenisId);
            e.printStackTrace();
            return false;
        }
    }




    // Método para obter os itens do carrinho
    public List<ItemCarrinho> obterItensCarrinho() {
        List<ItemCarrinho> itensCarrinho = new ArrayList<>();
        String sql = "SELECT * FROM Carrinho";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int tenisId = rs.getInt("tenis_id");
                int quantidade = rs.getInt("quantidade");
                Tenis tenis = new TenisDao().getTenisById(tenisId);
                itensCarrinho.add(new ItemCarrinho(tenis, quantidade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itensCarrinho;
    }

    public List<ItemCarrinho> obterItensCarrinho(int clienteId) {
        List<ItemCarrinho> itensCarrinho = new ArrayList<>();
        String sql = "SELECT c.tenis_id, c.quantidade " +
                "FROM Carrinho c " +
                "WHERE c.cliente_id = ?";  // Filtra pelo cliente_id

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, clienteId);  // Define o clienteId na consulta

            try (ResultSet rs = ps.executeQuery()) {
                TenisDao tenisDao = new TenisDao();  // Instancia o TenisDao para buscar os detalhes do tênis

                while (rs.next()) {
                    int tenisId = rs.getInt("tenis_id");
                    int quantidade = rs.getInt("quantidade");

                    // Recupera os detalhes do Tenis usando o TenisDao
                    Tenis tenis = tenisDao.getTenisById(tenisId);

                    if (tenis != null) {
                        // Adiciona o item ao carrinho
                        itensCarrinho.add(new ItemCarrinho(tenis, quantidade));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itensCarrinho;
    }

    // Método para limpar o carrinho de um cliente
    public boolean limparCarrinho(int clienteId) {
        String deleteSql = "DELETE FROM Carrinho WHERE cliente_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement deleteStmt = con.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, clienteId);  // Define o clienteId na consulta

            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0;  // Retorna true se ao menos uma linha foi afetada (itens removidos)

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false se ocorrer algum erro
        }
    }

    // Método para atualizar a quantidade de um item no carrinho
    public boolean atualizarQuantidade(int tenisId, int clienteId, int novaQuantidade) {
        String sql = "UPDATE Carrinho SET quantidade = ? WHERE cliente_id = ? AND tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, novaQuantidade);  // Define a nova quantidade
            ps.setInt(2, clienteId);        // Define o clienteId
            ps.setInt(3, tenisId);          // Define o tenisId

            int rowsAffected = ps.executeUpdate();  // Executa a atualização
            return rowsAffected > 0;  // Retorna true se a quantidade foi atualizada

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false em caso de erro
        }
    }

}
