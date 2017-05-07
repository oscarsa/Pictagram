package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by oscar on 7/05/17.
 */
@WebServlet(name = "NuevaPublicacionServlet")
public class NuevaPublicacionServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Estoy en post");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        String nick = "";

        out.println("Estoy en get");

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");
            //TODO Falta enviar el nick a la página y hacer la redirección bien
            request.getRequestDispatcher("/WEB-INF/nuevaPublicacion.jsp").forward(request,response);
        } else {
            //out.println("Sesión no iniciada!");
            response.sendRedirect("index.jsp");
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
