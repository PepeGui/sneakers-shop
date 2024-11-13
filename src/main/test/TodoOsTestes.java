import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodosOsTestes {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private UsuarioDao usuarioDao;

    @InjectMocks
    private LoginUsuarioServlet loginUsuarioServlet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginComSucesso() throws Exception {
        // Simula os parâmetros de entrada
        when(request.getParameter("email")).thenReturn("usuario@exemplo.com");
        when(request.getParameter("senha")).thenReturn("senha123");
        when(usuarioDao.verificarLogin(any(Usuario.class))).thenReturn(true);
        when(usuarioDao.buscarUsuarioPorEmail("usuario@exemplo.com")).thenReturn(new Usuario("usuario@exemplo.com", "senha123"));
        when(request.getRequestDispatcher("Principal/principal.jsp")).thenReturn(dispatcher);

        // Executa o servlet
        loginUsuarioServlet.doGet(request, response);

        // Verifica se foi direcionado para a página principal
        verify(request).getRequestDispatcher("Principal/principal.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testLoginComFalha() throws Exception {
        // Simula os parâmetros de entrada
        when(request.getParameter("email")).thenReturn("usuario@exemplo.com");
        when(request.getParameter("senha")).thenReturn("senhaErrada");
        when(usuarioDao.verificarLogin(any(Usuario.class))).thenReturn(false);
        when(request.getRequestDispatcher("Login/login.jsp")).thenReturn(dispatcher);

        // Executa o servlet
        loginUsuarioServlet.doGet(request, response);

        // Verifica se a mensagem de erro foi configurada corretamente
        verify(request).setAttribute("errorMessage", "Login inválido. Verifique o login e a senha!");
        verify(request).getRequestDispatcher("Login/login.jsp");
        verify(dispatcher).forward(request, response);

        // Usando Hamcrest para verificar a mensagem de erro
        assertThat(request.getAttribute("errorMessage"), is("Login inválido. Verifique o login e a senha!"));
    }

    @Test
    public void testErroNoServidor() throws IOException {
        // Simula uma exceção ao verificar o login
        when(request.getParameter("email")).thenReturn("usuario@exemplo.com");
        when(request.getParameter("senha")).thenReturn("senha123");
        doThrow(new RuntimeException("Erro no banco de dados")).when(usuarioDao).verificarLogin(any(Usuario.class));

        // Executa o servlet
        loginUsuarioServlet.doGet(request, response);

        // Verifica se a resposta foi enviada com erro 500
        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor. Tente novamente mais tarde.");
    }

    @Test
    public void testCadastrarTenisComSucesso() throws Exception {
        when(request.getParameter("nome")).thenReturn("Tênis Teste");
        when(request.getParameter("avaliacao")).thenReturn("4.5");
        when(request.getParameter("descricao")).thenReturn("Descrição do Tênis");
        when(request.getParameter("preco")).thenReturn("299.99");
        when(request.getParameter("estoque")).thenReturn("10");
        when(request.getParameter("principalImage")).thenReturn("0");
        when(request.getParts()).thenReturn(List.of(part));
        when(part.getName()).thenReturn("imagem");
        when(part.getHeader("content-disposition")).thenReturn("form-data; name=\"imagem\"; filename=\"tenis.jpg\"");
        when(tenisDao.createTenis(any(Tenis.class))).thenReturn(true);

        cadastrarTenisServlet.doPost(request, response);

        verify(response).sendRedirect("/find-all-tenis");
    }

    @Test
    public void testCadastrarTenisErroAoSalvar() throws Exception {
        when(request.getParameter("nome")).thenReturn("Tênis Teste");
        when(request.getParameter("avaliacao")).thenReturn("4.5");
        when(request.getParameter("descricao")).thenReturn("Descrição do Tênis");
        when(request.getParameter("preco")).thenReturn("299.99");
        when(request.getParameter("estoque")).thenReturn("10");
        when(request.getParameter("principalImage")).thenReturn("0");
        when(request.getParts()).thenReturn(List.of(part));
        when(part.getName()).thenReturn("imagem");
        when(part.getHeader("content-disposition")).thenReturn("form-data; name=\"imagem\"; filename=\"tenis.jpg\"");
        when(tenisDao.createTenis(any(Tenis.class))).thenReturn(false);

        cadastrarTenisServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao cadastrar tênis");
    }

    @Test
    public void testListarTodosTenis() throws Exception {
        when(tenisDao.getAllTenis()).thenReturn(List.of(new Tenis("Tênis Teste")));
        when(request.getRequestDispatcher("/Tenis/tenis.jsp")).thenReturn(dispatcher);

        listarTenisServlet.doGet(request, response);

        verify(request).setAttribute(eq("tenisList"), anyList());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testCadastroClienteComSucesso() throws Exception {
        when(request.getParameter("nome")).thenReturn("Cliente Teste");
        when(request.getParameter("dataNascimento")).thenReturn("2000-01-01");
        when(request.getParameter("genero")).thenReturn("M");
        when(request.getParameter("cpf")).thenReturn("12345678901");
        when(request.getParameter("email")).thenReturn("cliente@teste.com");
        when(request.getParameter("senha")).thenReturn("senha123");
        when(clienteDao.cadastrarCliente(any(Cliente.class))).thenReturn(true);

        cadastroClienteServlet.doPost(request, response);

        verify(response).sendRedirect("/");
    }

    @Test
    public void testCadastroClienteErro() throws Exception {
        when(request.getParameter("nome")).thenReturn("Cliente Teste");
        when(clienteDao.cadastrarCliente(any(Cliente.class))).thenReturn(false);

        cadastroClienteServlet.doPost(request, response);

        verify(response).sendRedirect("cadastro-cliente.jsp?erro=Erro ao cadastrar cliente.");
    }

    @Test
    public void testAdicionarItemAoCarrinhoComSucesso() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("quantidade")).thenReturn("2");
        when(carrinhoDao.adicionarItemAoCarrinho(1, 2)).thenReturn(true);

        adicionarCarrinhoServlet.doPost(request, response);

        verify(response).sendRedirect("/Carrinho/carrinho.jsp");
    }

    @Test
    public void testAdicionarItemAoCarrinhoComErro() throws Exception {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("quantidade")).thenReturn("2");
        when(carrinhoDao.adicionarItemAoCarrinho(1, 2)).thenReturn(false);

        adicionarCarrinhoServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao adicionar item ao carrinho.");
    }

    @Test
    public void testAdicionarItemAoCarrinhoParametrosInvalidos() throws Exception {
        when(request.getParameter("id")).thenReturn("abc");
        when(request.getParameter("quantidade")).thenReturn("xyz");

        adicionarCarrinhoServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetros inválidos.");
    }
}
