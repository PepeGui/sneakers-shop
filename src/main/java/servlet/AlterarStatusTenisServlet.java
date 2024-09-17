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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
       
        String id = req.getParameter("id");
        String novoStatus = req.getParameter("status");

        if (id == null || novoStatus == null){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID ou status ausente");
            return;
        }

        Tenis t = new Tenis(Integer.parseInt(id));
        t.setAtivo("ativo".equals(novoStatus));

        try{
            boolean sucesso = tenisDao.alterarStatus(t);
            if(sucesso){
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao atualizar o status");
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar o ID");
        }
    }
}