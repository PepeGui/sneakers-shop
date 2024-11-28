package dao;

import model.ImagemTenis;
import model.Tenis;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TenisDao {

    private static final String SQL_Select = "SELECT * FROM TENIS";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "sa";

    public boolean createTenis(Tenis tenis) {
        String sqlTenis = "INSERT INTO TENIS (nome, avaliacao, descricao, preco, estoque) VALUES (?, ?, ?, ?, ?)";
        String sqlImagem = "INSERT INTO IMAGEMTENIS (tenis_id, caminho, principal) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        PreparedStatement psTenis = con.prepareStatement(sqlTenis, Statement.RETURN_GENERATED_KEYS)) {

            psTenis.setString(1, tenis.getNome());
            psTenis.setDouble(2, tenis.getAvaliacao());
            psTenis.setString(3, tenis.getDescricao());
            psTenis.setDouble(4, tenis.getPreco());
            psTenis.setInt(5, tenis.getEstoque());

            psTenis.executeUpdate();

            ResultSet rs = psTenis.getGeneratedKeys();
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

    public List<Tenis> getAllTenis(boolean soTenisAtivo) {
        List<Tenis> tenisList = new ArrayList<>();
        String SQL_SELECT_ALL = soTenisAtivo != true? "SELECT * FROM TENIS" : "SELECT * FROM TENIS WHERE ATIVO = TRUE";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Tenis tenis = new Tenis();
                tenis.setId(resultSet.getInt("ID"));
                tenis.setNome(resultSet.getString("NOME"));
                tenis.setDescricao(resultSet.getString("DESCRICAO"));
                tenis.setPreco(resultSet.getDouble("PRECO"));
                tenis.setEstoque(resultSet.getInt("ESTOQUE"));
                tenis.setAvaliacao(resultSet.getDouble("AVALIACAO"));
                tenis.setAtivo(resultSet.getBoolean("ATIVO"));

                // Buscar e associar imagens ao tênis
                List<ImagemTenis> imagens = getImagensPorTenisId(tenis.getId());
                tenis.setImagens(imagens);

                // Definir a imagem principal, se houver
                for (ImagemTenis img : imagens) {
                    if (img.isPrincipal()) {
                        tenis.setImagem(img.getCaminho());
                        break;
                    }
                }

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
    public boolean atualizarTenis(Tenis tenis) {
        String sqlTenis = "UPDATE TENIS SET nome = ?, avaliacao = ?, descricao = ?, preco = ?, estoque = ? WHERE id = ?";
        //String sqlImagem = "UPDATE ImagemTenis SET caminho = ?, principal = ? WHERE id = ? AND tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            // Atualizar dados do tênis
            try (PreparedStatement psProduto = con.prepareStatement(sqlTenis)) {
                psProduto.setString(1, tenis.getNome());
                psProduto.setDouble(2, tenis.getAvaliacao());
                psProduto.setString(3, tenis.getDescricao());
                psProduto.setDouble(4, tenis.getPreco());
                psProduto.setInt(5, tenis.getEstoque());
                psProduto.setInt(6, tenis.getId()); // O ID do tênis a ser atualizado

                psProduto.executeUpdate();
            }

            // Atualizar as imagens associadas ao tênis
            /*for (ImagemTenis imagem : tenis.getImagens()) {
                try (PreparedStatement psImagem = con.prepareStatement(sqlImagem)) {
                    psImagem.setString(1, imagem.getCaminho());
                    psImagem.setBoolean(2, imagem.isPrincipal());
                    psImagem.setInt(3, imagem.getId()); // ID da imagem
                    psImagem.setInt(4, tenis.getId());  // ID do tênis

                    psImagem.executeUpdate();
                }
            }*/

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarEstoqueTenis(int pEstoque, int pIdTenis) {
        String sqlTenis = "UPDATE TENIS SET  estoque = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            // Atualizar dados do tênis
            try (PreparedStatement psProduto = con.prepareStatement(sqlTenis)) {
                psProduto.setInt(1, pEstoque);
                psProduto.setInt(2, pIdTenis);

                psProduto.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Tenis buscaTenisPorID(Tenis pTenis) {
        List<Tenis> tenis = new ArrayList<>();
        String SQL = "SELECT * FROM TENIS WHERE ID = ?";
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("success in database connection buscaTenisPorID");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, pTenis.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                String preco = resultSet.getString("preco");
                String estoque = resultSet.getString("estoque");
                String avaliacao = resultSet.getString("avaliacao");
                String ativo = resultSet.getString("ativo");
                tenis.add(new Tenis(Integer.parseInt(id), nome, Double.parseDouble(preco), Integer.parseInt(estoque), descricao, Double.parseDouble(avaliacao), Boolean.parseBoolean(ativo)));
            }
            System.out.println("success in buscaTenisPorID");

        } catch (Exception e) {
            System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }

        return tenis.getFirst();
    }

    public Tenis getTenisById(int tenisId){
        Tenis tenis = null;
        String SQL = "SELECT * FROM TENIS WHERE ID = ?";

        try{
            Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("success in database connection buscaUsuariosPorID");
            PreparedStatement preparedStatement = con.prepareStatement(SQL);

            preparedStatement.setInt(1,tenisId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double preco = resultSet.getDouble("preco");
                int estoque = resultSet.getInt("estoque");
                double avaliacao = resultSet.getDouble("avaliacao");
                boolean ativo = resultSet.getBoolean("ativo");

                //Cria o objeto tenis
                tenis = new Tenis(id,nome,preco,estoque,descricao,avaliacao,ativo);
            }

        } catch (Exception e){
            System.out.println("Erro ao getTenisByID: " + e.getMessage());
        }
        return tenis;
    }

    public List<ImagemTenis> getImagensPorTenisId(int tenisId) {
        List<ImagemTenis> imagens = new ArrayList<>();
        String SQL_SELECT_IMAGENS = "SELECT * FROM IMAGEMTENIS WHERE TENIS_ID = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_IMAGENS)) {

            preparedStatement.setInt(1, tenisId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ImagemTenis imagem = new ImagemTenis();
                    imagem.setId(resultSet.getInt("id"));
                    imagem.setTenisId(resultSet.getInt("tenis_id"));
                    imagem.setCaminho(resultSet.getString("caminho"));
                    imagem.setPrincipal(resultSet.getBoolean("principal"));
                    imagens.add(imagem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imagens;
    }

    public boolean atualizarTenisComImagens(Tenis tenis) {
        String sqlTenis = "UPDATE TENIS SET nome = ?, avaliacao = ?, descricao = ?, preco = ?, estoque = ? WHERE id = ?";
        String sqlInserirImagem = "INSERT INTO IMAGEMTENIS (tenis_id, caminho, principal) VALUES (?, ?, ?)";
        String sqlAtualizarImagem = "UPDATE IMAGEMTENIS SET caminho = ?, principal = ? WHERE id = ? AND tenis_id = ?";
        String sqlDesmarcarPrincipal = "UPDATE IMAGEMTENIS SET principal = false WHERE tenis_id = ?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

            // Atualizar dados do tênis
            try (PreparedStatement psTenis = con.prepareStatement(sqlTenis)) {
                psTenis.setString(1, tenis.getNome());
                psTenis.setDouble(2, tenis.getAvaliacao());
                psTenis.setString(3, tenis.getDescricao());
                psTenis.setDouble(4, tenis.getPreco());
                psTenis.setInt(5, tenis.getEstoque());
                psTenis.setInt(6, tenis.getId());
                psTenis.executeUpdate();
            }

            // Desmarcar qualquer imagem existente como principal
            try (PreparedStatement psDesmarcarPrincipal = con.prepareStatement(sqlDesmarcarPrincipal)) {
                psDesmarcarPrincipal.setInt(1, tenis.getId());
                psDesmarcarPrincipal.executeUpdate();
            }

            // Atualizar ou inserir imagens
            for (ImagemTenis imagem : tenis.getImagens()) {
                if (imagem.getId() > 0) { // Atualizar imagem existente
                    try (PreparedStatement psAtualizarImagem = con.prepareStatement(sqlAtualizarImagem)) {
                        psAtualizarImagem.setString(1, imagem.getCaminho());
                        psAtualizarImagem.setBoolean(2, imagem.isPrincipal());
                        psAtualizarImagem.setInt(3, imagem.getId());
                        psAtualizarImagem.setInt(4, tenis.getId());
                        psAtualizarImagem.executeUpdate();
                    }
                } else { // Inserir nova imagem
                    try (PreparedStatement psInserirImagem = con.prepareStatement(sqlInserirImagem)) {
                        psInserirImagem.setInt(1, tenis.getId());
                        psInserirImagem.setString(2, imagem.getCaminho());
                        psInserirImagem.setBoolean(3, imagem.isPrincipal());
                        psInserirImagem.executeUpdate();
                    }
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
