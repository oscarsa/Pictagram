package controller;

import model.FotoDAO;
import model.FotoVO;
import model.UsuarioDAO;
import model.UsuarioVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Creado por Óscar Saboya e Ían Ávila el 18/05/17.
 */
@WebServlet(name = "AmistadesServlet")
public class AmistadesServlet extends HttpServlet {

    private DataSource ds;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            InitialContext ctx = new InitialContext();
            Context env = (Context) ctx.lookup("java:comp/env");
            // Nombre del recurso en el context.xml
            ds = (DataSource) env.lookup("jdbc/ConexionMySQL");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        boolean error = false;
        HttpSession session = request.getSession(false);

        String nick = "";

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");
            //out.println("Sesión iniciada, nick: " + nick);
        } else {
            //out.println("Sesión no iniciada!");
            error=true;
        }


        if(error){
            response.sendRedirect("index.jsp");
        } else{
            UsuarioDAO usuarioDAO = new UsuarioDAO(ds);
            ArrayList<UsuarioVO> amigos = usuarioDAO.obtenerAmigos(nick);
            ArrayList<UsuarioVO> noAmigos = usuarioDAO.obtenerNoAmigos(nick);
            ArrayList<UsuarioVO> peticionesAmistad = usuarioDAO.obtenerPeticionesAmigos(nick);
            request.setAttribute("listaAmigos",amigos);
            request.setAttribute("listaNoAmigos",noAmigos);
            request.setAttribute("listaPeticionesAmistad",peticionesAmistad);
            request.getRequestDispatcher("/WEB-INF/amistades.jsp").forward(request,response);

        }
    }
}
