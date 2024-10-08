package servlet;

import dao.UsuarioDao;
import model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/find-all-usuarios")

public class ListarUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        List<Usuario> users = new ArrayList<>();
        UsuarioDao usuarioDao = new UsuarioDao();

        String pesquisa = req.getParameter("pesquisa");

        if(pesquisa == null || pesquisa.isBlank() )
            users.addAll(usuarioDao.getAllUsuarios());
        else
            users.addAll(usuarioDao.buscaUsuariosPorNome(pesquisa));

        req.setAttribute("usuarios", users);

        req.getRequestDispatcher("Listar/listar.jsp").forward(req,resp);
    }
}