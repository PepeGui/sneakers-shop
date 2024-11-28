package servlet;

import dao.CarrinhoDao;
import dao.EnderecoDao;
import model.Endereco;
import model.ItemCarrinho;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/resumo-pedido")
public class GerarResumoPedidoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // DAOs para buscar informações
            EnderecoDao enderecoDao = new EnderecoDao();
            CarrinhoDao carrinhoDao = new CarrinhoDao();

            // Sessão do usuário
            HttpSession session = req.getSession();

            // Obtendo IDs do cliente e do endereço da sessão
            Integer enderecoId = (Integer) session.getAttribute("enderecoId");
            Integer clienteId = (Integer) session.getAttribute("clienteId");

            // Obtendo forma de pagamento do formulário
            String formaPagamento = req.getParameter("formaPagamento");
            if (formaPagamento == null || formaPagamento.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Forma de pagamento não selecionada.");
                return;
            }

            // Busca informações do endereço
            Endereco enderecoSelecionado = enderecoDao.buscaEnderecoPorID(enderecoId);

            // Busca itens do carrinho
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho(clienteId);

            // Calcula valores do pedido
            double subtotal = itensCarrinho.stream()
                    .mapToDouble(item -> item.getQuantidade() * item.getTenis().getPreco())
                    .sum();
            double frete = 30.0; // Frete fixo de R$30
            double total = subtotal + frete;

            // Define os atributos para a página JSP
            req.setAttribute("enderecoSelecionado", enderecoSelecionado);
            req.setAttribute("itensCarrinho", itensCarrinho);
            req.setAttribute("formaPagamento", formaPagamento);
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("frete", frete);
            req.setAttribute("total", total);

            // Encaminha para a página de resumo do pedido
            RequestDispatcher dispatcher = req.getRequestDispatcher("/Resumo-Pedido/resumo-pedido.jsp");
            dispatcher.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o resumo do pedido.");
        }
    }
}
