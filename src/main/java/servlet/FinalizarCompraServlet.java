package servlet;

import dao.CarrinhoDao;
import dao.EnderecoDao;
import model.Endereco;
import model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/finalizar-compra")
public class FinalizarCompraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtém o ID do cliente da sessão
            HttpSession session = req.getSession();
            Integer clienteId = (Integer) session.getAttribute("clienteId");
            System.out.println(clienteId);

            if (clienteId == null) {
                // Se o cliente não estiver logado, redireciona para a página de login
                resp.sendRedirect("/login-cliente.jsp");
                return;
            }

            // Busca os endereços do cliente
            EnderecoDao enderecoDao = new EnderecoDao();
            List<Endereco> enderecos = enderecoDao.buscarEnderecosPorClienteId(clienteId);
            if (enderecos.isEmpty()) {
                System.out.println("Nenhum endereço encontrado para o cliente ID: " + clienteId);
            } else {
                System.out.println("Endereços encontrados: " + enderecos.size());
                for (Endereco e : enderecos) {
                    System.out.println(e);
                }
            }

            // Passa os endereços para a página JSP
            req.setAttribute("enderecos", enderecos);

            // Processa os itens do carrinho (você pode ajustar a lógica conforme necessário)
            CarrinhoDao carrinhoDao = new CarrinhoDao();
            List<ItemCarrinho> itensCarrinho = carrinhoDao.obterItensCarrinho();
            req.setAttribute("itensCarrinho", itensCarrinho);

            // Calcula subtotal e total
            double subtotal = 0.0;
            for (ItemCarrinho item : itensCarrinho) {
                subtotal += item.getTenis().getPreco() * item.getQuantidade();
            }
            double total = subtotal + 30.0; // considerando o frete de 30
            req.setAttribute("subtotal", subtotal);
            req.setAttribute("total", total);

            // Redireciona para o JSP de finalização de compra
            req.getRequestDispatcher("/Finalizar-Compra/finalizar-compra.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao buscar endereços. Tente novamente.");
        }
    }
}
