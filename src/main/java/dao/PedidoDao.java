package dao;

import model.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    // Conecta ao banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // Cria um pedido
    public int criarPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (cliente_id, data_pedido, status, valor_total, endereco_entrega_id) VALUES (?, NOW(), ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getClienteId());
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setInt(4, pedido.getEnderecoEntregaId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return 0; // Retorna 0 em caso de falha
    }

    // Obtém os pedidos de um cliente
    public List<Pedido> obterPedidosPorCliente(int clienteId) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido WHERE cliente_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setClienteId(rs.getInt("cliente_id"));
                    pedido.setDataPedido(rs.getTimestamp("data_pedido"));
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    pedido.setEnderecoEntregaId(rs.getInt("endereco_entrega_id"));
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }
}
