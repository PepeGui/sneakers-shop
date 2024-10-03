package servlet;

import dao.TenisDao;
import model.ImagemTenis;
import model.Tenis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/alterar-Tenis")
public class AlterarTenisServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TenisDao tenisDao = new TenisDao();

        // Obtém os parâmetros da requisição
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        String estoqueStr = request.getParameter("estoque");
        String descricao = request.getParameter("descricao");
        String avaliacaoStr = request.getParameter("avaliacao");
        String caminhoImagem = request.getParameter("imagem");  // Caminho da imagem no sistema
        String imagemPrincipalStr = request.getParameter("imagemPrincipal"); // Verifica se é principal

        // Valida se os parâmetros obrigatórios estão presentes
        if (idStr == null || nome == null || precoStr == null || estoqueStr == null || avaliacaoStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos obrigatórios devem ser preenchidos.");
            return;
        }

        try {
            // Converte os valores numéricos
            int id = Integer.parseInt(idStr);
            double preco = Double.parseDouble(precoStr);
            int estoque = Integer.parseInt(estoqueStr);
            double avaliacao = Double.parseDouble(avaliacaoStr);
            boolean imagemPrincipal = Boolean.parseBoolean(imagemPrincipalStr); // Converte string para booleano

            // Cria um objeto Tênis com as novas informações
            Tenis tenis = new Tenis();
            tenis.setId(id);
            tenis.setNome(nome);
            tenis.setPreco(preco);
            tenis.setEstoque(estoque);
            tenis.setDescricao(descricao);
            tenis.setAvaliacao(avaliacao);

            // Cria a lista de imagens e adiciona a imagem recebida
            List<ImagemTenis> listaImagens = new ArrayList<>();
            if (caminhoImagem != null && !caminhoImagem.isEmpty()) {
                ImagemTenis imagemTenis = new ImagemTenis();
                imagemTenis.setCaminho(caminhoImagem);
                imagemTenis.setPrincipal(imagemPrincipal);
                listaImagens.add(imagemTenis);
            }

            // Seta a lista de imagens no objeto Tênis
            tenis.setImagens(listaImagens);

            // Atualiza o tênis no banco de dados
            boolean atualizado = tenisDao.atualizarTenis(tenis);

            if (atualizado) {
                // Redireciona para a página de sucesso ou lista de produtos
                response.sendRedirect("/find-all-tenis");
            } else {
                // Envia uma mensagem de erro se a atualização falhar
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o produto.");
            }

        } catch (NumberFormatException e) {
            // Lida com erros de conversão de tipos numéricos
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato inválido para preço, estoque ou avaliação.");
        }
    }
}
