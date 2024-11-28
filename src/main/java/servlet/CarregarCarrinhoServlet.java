package servlet;

import dao.CarrinhoDao;
import model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

@WebServlet("/carrinho")
public class CarregarCarrinhoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém o clienteId da sessão
        HttpSession session = request.getSession();
        Integer clienteId = (Integer) session.getAttribute("clienteId");

        if (clienteId == null) {
            // Se o clienteId não estiver na sessão, redireciona para a página de login
            response.sendRedirect("/login.jsp");
            return;
        }

        // Cria o DAO e chama o método com o clienteId
        CarrinhoDao carrinhoDao = new CarrinhoDao();
        List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);

        // Calcula os valores subtotal e total
        double subtotal = 0.0;
        for (ItemCarrinho item : itensCarrinho) {
            subtotal += item.getTenis().getPreco() * item.getQuantidade();
        }

        double frete = 30.0;
        double total = subtotal + frete;

        // Formata os valores para exibição
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String subtotalFormatado = "R$ " + df.format(subtotal);
        String totalFormatado = "R$ " + df.format(total);

        // Adiciona os atributos à requisição
        request.setAttribute("itensCarrinho", itensCarrinho);
        request.setAttribute("subtotal", subtotalFormatado);
        request.setAttribute("frete", "R$ " + df.format(frete));
        request.setAttribute("total", totalFormatado);

        // Encaminha para o JSP
        request.getRequestDispatcher("/Carrinho/carrinho.jsp").forward(request, response);
    }
}
