package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/processarPagamento")
public class ProcessarPagamentoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formaPagamento = request.getParameter("formaPagamento");

        if (formaPagamento == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Forma de pagamento não selecionada.");
            return;
        }

        if ("cartao".equals(formaPagamento)) {
            // Validar campos do cartão
            String numeroCartao = request.getParameter("numeroCartao");
            String codigoVerificador = request.getParameter("codigoVerificador");
            String nomeCompleto = request.getParameter("nomeCompleto");
            String dataVencimento = request.getParameter("dataVencimento");
            String parcelas = request.getParameter("parcelas");

            if (numeroCartao == null || numeroCartao.isEmpty() ||
                    codigoVerificador == null || codigoVerificador.isEmpty() ||
                    nomeCompleto == null || nomeCompleto.isEmpty() ||
                    dataVencimento == null || dataVencimento.isEmpty() ||
                    parcelas == null || parcelas.isEmpty()) {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos os campos do cartão devem ser preenchidos.");
                return;
            }
        }

        // A forma de pagamento foi selecionada e está correta
        // Redirecionar para a página de validação do pedido final
        response.sendRedirect("/validarPedidoFinal.jsp");
    }
}

