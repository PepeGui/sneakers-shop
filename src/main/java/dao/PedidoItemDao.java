package dao;

import model.PedidoItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoItemDao {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    // Conecta ao banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Adiciona itens ao pedido
    public void adicionarItensAoPedido(List<PedidoItem> itens) throws SQLException {
        String sql = "INSERT INTO PedidoItem (pedido_id, tenis_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        String sqlCheckTenis = "SELECT COUNT(*) FROM Tenis WHERE id = ?"; // Verifica se o tenis_id existe

        try (Connection conn = getConnection();
             PreparedStatement stmtCheckTenis = conn.prepareStatement(sqlCheckTenis);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Inicia uma transação
            conn.setAutoCommit(false); // Desativa auto-commit para controlar a transação manualmente

            try {
                for (PedidoItem item : itens) {
                    // Verifica se o tenis_id existe
                    stmtCheckTenis.setInt(1, item.getTenisId());
                    try (ResultSet rs = stmtCheckTenis.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            throw new SQLException("O Tenis com ID " + item.getTenisId() + " não existe.");
                        }
                    }

                    // Inserção no PedidoItem
                    stmt.setInt(1, item.getPedidoId());
                    stmt.setInt(2, item.getTenisId());
                    stmt.setInt(3, item.getQuantidade());
                    stmt.setDouble(4, item.getPrecoUnitario());
                    stmt.addBatch();
                }

                // Executa o batch de inserção
                stmt.executeBatch();
                conn.commit(); // Confirma a transação

            } catch (SQLException e) {
                conn.rollback(); // Em caso de erro, reverte as inserções
                throw new SQLException("Erro ao adicionar itens ao pedido: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // Restaura o auto-commit
            }
        }
    }


    // Obtém os itens de um pedido
    public List<PedidoItem> obterItensPorPedido(int pedidoId) throws SQLException {
        List<PedidoItem> itens = new ArrayList<>();
        String sql = "SELECT * FROM PedidoItem WHERE pedido_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PedidoItem item = new PedidoItem();
                    item.setId(rs.getInt("id"));
                    item.setPedidoId(rs.getInt("pedido_id"));
                    item.setTenisId(rs.getInt("tenis_id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setPrecoUnitario(rs.getDouble("preco_unitario"));
                    itens.add(item);
                }
            }
        }
        return itens;
    }
}
