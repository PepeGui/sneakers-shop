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
import java.util.List;
import java.util.Map;

@WebServlet("/atualizarCarrinho")
public class AtualizarCarrinhoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtém o clienteId da sessão
        HttpSession session = request.getSession();
        Integer clienteId = (Integer) session.getAttribute("clienteId");

        if (clienteId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Você precisa estar logado.");
            return;
        }

        // Itera sobre os parâmetros recebidos do formulário para atualizar a quantidade
        CarrinhoDao carrinhoDao = new CarrinhoDao();

        // Para cada item no carrinho, verifica se a quantidade foi alterada
        for (String paramName : request.getParameterMap().keySet()) {
            if (paramName.startsWith("quantidade-")) {
                // Extrai o ID do tênis a partir do nome do parâmetro
                int tenisId = Integer.parseInt(paramName.substring(11)); // Remove "quantidade-" do nome do parâmetro
                int novaQuantidade = Integer.parseInt(request.getParameter(paramName));

                // Atualiza a quantidade do item no carrinho no banco de dados
                carrinhoDao.atualizarQuantidade(tenisId, clienteId, novaQuantidade);
            }
        }

        // Obtém os itens do carrinho atualizados
        List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);

        // Recalcula o subtotal e total
        double subtotal = 0.0;
        for (ItemCarrinho item : itensCarrinho) {
            subtotal += item.getTenis().getPreco() * item.getQuantidade();
        }

        double frete = 30.0; // Exemplo de valor fixo de frete
        double total = subtotal + frete;

        // Define os atributos para a página JSP
        request.setAttribute("itensCarrinho", itensCarrinho);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("total", total);
        request.setAttribute("frete", frete);

        // Redireciona para a página do carrinho
        response.sendRedirect("/carrinho");
    }
}


