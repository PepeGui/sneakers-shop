package servlet;

import dao.TenisDao;
import model.ImagemTenis;
import model.Tenis;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/alterar-tenis")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class AlterarTenisServlet extends HttpServlet {

    private TenisDao tenisDao = new TenisDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera os parâmetros do formulário
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String precoStr = request.getParameter("preco");
        String estoqueStr = request.getParameter("estoque");
        String descricao = request.getParameter("descricao");
        String avaliacaoStr = request.getParameter("avaliacao");
        String principalImageIdStr = request.getParameter("principalImageId"); // ID da imagem principal

        // Logando os parâmetros recebidos
        System.out.println("ID: " + idStr);
        System.out.println("Nome: " + nome);
        System.out.println("Preço: " + precoStr);
        System.out.println("Estoque: " + estoqueStr);
        System.out.println("Descrição: " + descricao);
        System.out.println("Avaliação: " + avaliacaoStr);
        System.out.println("Principal Image ID: " + principalImageIdStr);

        try {
            // Validar e converter os parâmetros
            int id = idStr != null && !idStr.isEmpty() ? Integer.parseInt(idStr) : 0;
            double preco = precoStr != null && !precoStr.isEmpty() ? Double.parseDouble(precoStr) : 0.0;
            int estoque = estoqueStr != null && !estoqueStr.isEmpty() ? Integer.parseInt(estoqueStr) : 0;
            double avaliacao = avaliacaoStr != null && !avaliacaoStr.isEmpty() ? Double.parseDouble(avaliacaoStr) : 0.0;

            // Criar o objeto Tenis
            Tenis tenis = new Tenis();
            tenis.setId(id);
            tenis.setNome(nome);
            tenis.setPreco(preco);
            tenis.setEstoque(estoque);
            tenis.setDescricao(descricao);
            tenis.setAvaliacao(avaliacao);

            // Processar as imagens carregadas
            List<ImagemTenis> novasImagens = new ArrayList<>();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Verificar as imagens carregadas
            for (Part part : request.getParts()) {
                if (part.getName().startsWith("imagens")) {  // Usar o mesmo nome de campo do formulário
                    String fileName = extractFileName(part);
                    if (!fileName.isEmpty()) {
                        String filePath = uploadPath + File.separator + fileName;
                        part.write(filePath);  // Salvar a imagem no servidor

                        // Criar objeto ImagemTenis e adicionar à lista
                        ImagemTenis imagem = new ImagemTenis();
                        imagem.setCaminho("uploads" + File.separator + fileName);
                        novasImagens.add(imagem);
                    }
                }
            }

            // Logando informações sobre as imagens carregadas
            System.out.println("Imagens carregadas: " + novasImagens.size());

            // Configurar imagem principal, se selecionada
            if (principalImageIdStr != null && !principalImageIdStr.isEmpty()) {
                int principalImageId = Integer.parseInt(principalImageIdStr);  // Índice da imagem principal
                if (principalImageId >= 0 && principalImageId < novasImagens.size()) {
                    ImagemTenis imagemPrincipal = novasImagens.get(principalImageId);
                    imagemPrincipal.setPrincipal(true);
                    // Logando a imagem principal configurada
                    System.out.println("Imagem principal configurada: " + imagemPrincipal.getCaminho());
                }
            }

            // Atualizar lista de imagens no objeto `Tenis`
            tenis.setImagens(novasImagens);

            // Atualizar tênis no banco de dados
            boolean sucesso = tenisDao.atualizarTenisComImagens(tenis);

            if (sucesso) {
                response.sendRedirect("/find-all-tenis");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o produto.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar a solicitação.");
        }
    }

    // Método auxiliar para extrair o nome do arquivo da parte
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String item : contentDisp.split(";")) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
}
