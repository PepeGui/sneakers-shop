package servlet;

import dao.CarrinhoDao;
import dao.EnderecoDao;
import model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/resumo-pedido")
public class GerarResumoPedidoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        try{
            EnderecoDao enderecoDao = new EnderecoDao();
            CarrinhoDao carrinhoDao = new CarrinhoDao();

            int enderecoId = Integer.parseInt(req.getParameter("enderecoId"));

            HttpSession session = req.getSession();
            session.setAttribute("enderecoId", enderecoId);

            Integer clienteId = (Integer) session.getAttribute("clienteId");

            enderecoDao.buscaEnderecoPorID(enderecoId);

            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);

            req.setAttribute("itensCarrinho", itensCarrinho);


        } catch (Exception err) {
            err.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Erro no gerar resumo");
        }
    }
}
