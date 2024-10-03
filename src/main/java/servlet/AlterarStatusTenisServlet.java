package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TenisDao;
import model.Tenis;

@WebServlet("/alterarStatusTenis")
public class AlterarStatusTenisServlet extends HttpServlet{
    TenisDao tenisDao = new TenisDao();
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String novoStatus = request.getParameter("status");

        if (id == null || novoStatus == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ou status ausente");
            return;
        }

        Tenis u = new Tenis(Integer.parseInt(id));
        u.setAtivo("ativo".equalsIgnoreCase(novoStatus));  // Define o status baseado no parâmetro recebido

        try {
            boolean sucesso = tenisDao.alterarStatus(u);
            if (sucesso) {
                response.setStatus(HttpServletResponse.SC_OK);
                request.getRequestDispatcher("/Tenis/tenis.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o status");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar o ID");
        }
    }
}
