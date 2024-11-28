package dao;

import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    // Cria um pedido
    public int criarPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (cliente_id, data_pedido, status, valor_total, endereco_entrega_id, forma_pagamento) VALUES (?, NOW(), ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getClienteId());
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setInt(4, pedido.getEnderecoEntregaId());
            stmt.setString(5, pedido.getFormaPagamento());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Retorna o ID gerado do pedido
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

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
                    pedido.setFormaPagamento(rs.getString("forma_pagamento"));
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }

    // Obtém o pedido por ID, com os itens
    public Pedido buscaPedidoPorID(int pId) {
        List<Pedido> pedidos = new ArrayList<>();
        String SQL = "SELECT * FROM Pedido WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1,pId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setClienteId(resultSet.getInt("cliente_id"));
                pedido.setDataPedido(resultSet.getTimestamp("data_pedido"));
                pedido.setStatus(resultSet.getString("status"));
                pedido.setValorTotal(resultSet.getDouble("valor_total"));
                pedido.setEnderecoEntregaId(resultSet.getInt("endereco_entrega_id"));
                pedido.setFormaPagamento(resultSet.getString("forma_pagamento"));

                // Obtém os itens do pedido
                pedido.setItens(new PedidoItemDao().obterItensPorPedido(pedido.getId()));
                pedidos.add(pedido);
            }
            System.out.println("success in buscaPedidoPorID");

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return pedidos.isEmpty() ? null : pedidos.get(0); // Retorna o primeiro pedido ou null se não encontrar
    }

    // Obtém todos os pedidos
    public List<Pedido> getAllPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("id"));
                    pedido.setClienteId(rs.getInt("cliente_id"));
                    pedido.setDataPedido(rs.getTimestamp("data_pedido"));
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    pedido.setEnderecoEntregaId(rs.getInt("endereco_entrega_id"));
                    pedido.setFormaPagamento(rs.getString("forma_pagamento"));
                    pedido.setItens(new PedidoItemDao().obterItensPorPedido(pedido.getId()));
                    pedidos.add(pedido);
                }
            }
        }
        return pedidos;
    }
    public boolean alterarStatus(Pedido pedido) {
        String SQL = "UPDATE PEDIDO SET STATUS = ? WHERE ID = ?";
        boolean sucesso = false;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1, pedido.getStatus());  // Usa o status booleano do objeto
            preparedStatement.setInt(2, pedido.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            sucesso = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace(); // Log para depuração
        }

        return sucesso;
    }
}
