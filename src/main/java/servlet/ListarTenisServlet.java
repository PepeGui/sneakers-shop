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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/find-all-tenis")
public class ListarTenisServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Tenis> tenisList = new ArrayList<>();
        TenisDao tenisDao = new TenisDao();

        String pesquisa = req.getParameter("pesquisa");

        // Verifica se a pesquisa está nula ou em branco
        if (pesquisa == null || pesquisa.isBlank()) {
            // Adiciona todos os tênis se não houver pesquisa
            tenisList.addAll(tenisDao.getAllTenis());
        } else {
            // Busca os tênis por nome
            tenisList.addAll(tenisDao.buscarTenisPorNome(pesquisa));
        }

        for(Tenis tenis : tenisList){
            tenis.setImagem(tenis.getImagem().replace("\\", "/"));
        }

        for(Tenis tenis : tenisList){
            for(ImagemTenis imagemTenis : tenis.getImagens()) {
                imagemTenis.setCaminho(tenis.getImagem().replace("\\", "/"));
            }
        }

        // Atribui a lista de tênis à requisição
        req.setAttribute("tenisList", tenisList);

        // Redireciona para a página JSP que lista os tênis
        req.getRequestDispatcher("/Tenis/tenis.jsp").forward(req, resp);
    }
}
