package controller;

import model.FotoDAO;
import model.FotoVO;

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
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by oscar on 3/05/17.
 */
@WebServlet(name = "PortadaServlet")
public class PortadaServlet extends HttpServlet {

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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        boolean error = false;
        String nick = "";

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");
            //out.println("Sesión iniciada, nick: " + nick);
        } else {
            //out.println("Sesión no iniciada!");
            error = true;
        }

        if(error){
            response.sendRedirect("index.jsp");
        } else {
            try {
                FotoDAO fotoDAO = new FotoDAO(ds);
                ArrayList<FotoVO> listaFotos = fotoDAO.fotosDeAmigos(nick);
                request.setAttribute("listaFotos", listaFotos);
                request.getRequestDispatcher("/WEB-INF/portada.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace(System.err);
                out.println("Stack Trace:<br/>");
                out.println("<br/><br/>Stack Trace (for web display):</br>");
                out.println(displayErrorForWeb(e));
            }
        }
    }

    public String displayErrorForWeb(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String stackTrace = sw.toString();
        return stackTrace.replace(System.getProperty("line.separator"), "<br/>\n");
    }
}
