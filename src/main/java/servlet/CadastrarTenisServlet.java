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

@WebServlet("/cadastrarTenis")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CadastrarTenisServlet extends HttpServlet {

    private TenisDao tenisDao = new TenisDao();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recebe os parâmetros do formulário
        String nome = request.getParameter("nome");
        double avaliacao = Double.parseDouble(request.getParameter("avaliacao"));
        String descricao = request.getParameter("descricao");
        double preco = Double.parseDouble(request.getParameter("preco"));
        int estoque = Integer.parseInt(request.getParameter("estoque"));

        // Cria o objeto Tenis
        Tenis tenis = new Tenis();
        tenis.setNome(nome);
        tenis.setAvaliacao(avaliacao);
        tenis.setDescricao(descricao);
        tenis.setPreco(preco);
        tenis.setEstoque(estoque);

        // Processar as imagens enviadas
        List<ImagemTenis> imagens = new ArrayList<>();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // Índice da imagem principal
        int principalImageIndex = Integer.parseInt(request.getParameter("principalImage"));

        int index = 0;
        for (Part part : request.getParts()) {
            if (part.getName().equals("imagem")) {
                String fileName = extractFileName(part);
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);  // Salva a imagem no servidor

                // Verifica se essa imagem é a principal
                boolean principal = (index == principalImageIndex);

                // Adiciona a imagem ao tênis
                ImagemTenis imagem = new ImagemTenis();
                String relativePath = "uploads" + File.separator + fileName;
                imagem.setCaminho(relativePath);
                imagem.setPrincipal(principal);
                imagens.add(imagem);

                index++;
            }
        }

        tenis.setImagens(imagens);

        // Salvar o tênis no banco de dados
        boolean sucesso = tenisDao.createTenis(tenis);

        if (sucesso) {
            response.sendRedirect("/find-all-tenis");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar tênis");
        }
    }

    // Metodo auxiliar para extrair o nome do arquivo
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}

