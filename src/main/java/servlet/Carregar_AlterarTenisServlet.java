package servlet;

import dao.TenisDao;
import model.Tenis;
import model.ImagemTenis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


    @WebServlet("/tela-alterarTenis")
    public class Carregar_AlterarTenisServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Tenis tenis = new Tenis();
            HttpSession session = request.getSession();
            String usuarioGrupo = (String) session.getAttribute("usuarioGrupo");
            try {
                String id = request.getParameter("id");

                tenis.setId(Integer.parseInt(id));
                TenisDao tenisDao = new TenisDao();
                tenis = tenisDao.buscaTenisPorID(tenis);
            }   catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Formato de ID inválido. Por favor, forneça um ID numérico.");
        }
        request.setAttribute("tenis", tenis);
        request.setAttribute("grupo", usuarioGrupo);

        request.getRequestDispatcher("/AlterarTenis/alterartenis.jsp").forward(request,response);
        }
    }
