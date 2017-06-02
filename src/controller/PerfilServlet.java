package controller;

import model.AmistadesDAO;
import model.RelacionDAO;
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
 * Creado por Óscar Saboya e Ían Ávila el  25/05/17.
 */
@WebServlet(name = "PerfilServlet")
public class PerfilServlet extends HttpServlet {


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
        //Control de sesión de usuario
        HttpSession session = request.getSession(false);
        String nick = "";

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");

        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        String nickAmigo = request.getParameter("nickAmigo");

        AmistadesDAO amistadesDAO = new AmistadesDAO(ds);
        RelacionDAO relacionDAO = new RelacionDAO(ds);

        if (request.getParameter("botonEliminar") != null) {

            relacionDAO.eliminarRelacion(nick,nickAmigo);

        } else if (request.getParameter("botonConfirmar") != null) {

            amistadesDAO.confirmarPeticion(nickAmigo,nick);

        } else if (request.getParameter("botonPeticion") != null) {

            amistadesDAO.enviarPeticion(nick,nickAmigo);
        }


        boolean hayRelacion = relacionDAO.tienenRelacion(nick,nickAmigo);
        boolean haMandadoPeticion = amistadesDAO.haMandadoPeticion(nick,nickAmigo);
        boolean haRecibidoPeticion = amistadesDAO.haMandadoPeticion(nickAmigo,nick);

        UsuarioDAO usuarioDAO = new UsuarioDAO(ds);
        UsuarioVO perfilAmistad = usuarioDAO.obtenerUsuario(nickAmigo);
        String emailAmistad = perfilAmistad.getEmail();



        if(hayRelacion) {
            request.setAttribute("hayRelacion",hayRelacion);
        } else if(haMandadoPeticion) {
            request.setAttribute("haMandadoPeticion",haMandadoPeticion);
        } else if(haRecibidoPeticion) {
            request.setAttribute("haRecibidoPeticion",haRecibidoPeticion);
        }

        request.setAttribute("nickAmistad",nickAmigo);
        request.setAttribute("emailAmistad",emailAmistad);

        request.getRequestDispatcher("/WEB-INF/perfil.jsp?nick="+nickAmigo).forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        response.setContentType("text/html");

        boolean error = false;
        HttpSession session = request.getSession(false);

        String nick = "";

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");
        } else {
            error=true;
        }

        String nickAmistad = request.getParameter("nick");
        UsuarioDAO usuarioDAO = new UsuarioDAO(ds);
        UsuarioVO perfilAmistad = usuarioDAO.obtenerUsuario(nickAmistad);
        String emailAmistad = perfilAmistad.getEmail();

        RelacionDAO relacionDAO = new RelacionDAO(ds);
        AmistadesDAO amistadesDAO = new AmistadesDAO(ds);
        boolean hayRelacion = relacionDAO.tienenRelacion(nick,nickAmistad);
        boolean haMandadoPeticion = amistadesDAO.haMandadoPeticion(nick,nickAmistad);
        boolean haRecibidoPeticion = amistadesDAO.haMandadoPeticion(nickAmistad,nick);


        if(error){
            response.sendRedirect("index.jsp");
        } else{
            if(hayRelacion) {
                request.setAttribute("hayRelacion",hayRelacion);
            } else if(haMandadoPeticion) {
                request.setAttribute("haMandadoPeticion",haMandadoPeticion);
            } else if(haRecibidoPeticion) {
                request.setAttribute("haRecibidoPeticion",haRecibidoPeticion);
            }

            request.setAttribute("nickAmistad",nickAmistad);
            request.setAttribute("emailAmistad",emailAmistad);
            request.getRequestDispatcher("/WEB-INF/perfil.jsp").forward(request,response);

        }
    }
}
