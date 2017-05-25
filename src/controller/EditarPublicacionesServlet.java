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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by oscar on 10/05/17.
 */
@WebServlet(name = "EditarPublicacionesServlet")
public class EditarPublicacionesServlet extends HttpServlet {

    File uploads = new File("/home/oscar/IdeaProjects/Pictagram/out/artifacts/untitled_war_exploded/imagenes");

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
            //out.println("Sesión no iniciada!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        String idPublicacionString = request.getParameter("botonEliminar");
        int idPublicacion;

        PrintWriter out = response.getWriter();

        if(idPublicacionString == null || idPublicacionString.equals("")) {
            request.setAttribute("error","Error, no se ha podido eliminar la publicación");
            request.getRequestDispatcher("/WEB-INF/editarPublicaciones.jsp").forward(request,response);
        } else {
            idPublicacion = Integer.parseInt(idPublicacionString);
            FotoDAO fotoDAO = new FotoDAO(ds);
            FotoVO fotoVO = fotoDAO.obtenerFoto(idPublicacion);
            String ruta = fotoVO.getFoto();
            File deleteFile = new File(uploads+"/"+ruta);
            if( deleteFile.exists() ) {
                if(fotoDAO.eliminarPublicacion(idPublicacion)) {
                    deleteFile.delete() ;
                    ArrayList<FotoVO> listaFotos = fotoDAO.misFotos(nick);
                    request.setAttribute("listaFotos", listaFotos);
                    request.getRequestDispatcher("/WEB-INF/editarPublicaciones.jsp").forward(request,response);
                } else {
                    request.setAttribute("error","Error, no se ha podido eliminar la publicación");
                    request.getRequestDispatcher("/WEB-INF/editarPublicaciones.jsp").forward(request,response);
                }
            }  else {
                request.setAttribute("error","Error, no se ha podido eliminar la publicación");
                request.getRequestDispatcher("/WEB-INF/editarPublicaciones.jsp").forward(request,response);
            }


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
                ArrayList<FotoVO> listaFotos = fotoDAO.misFotos(nick);
                request.setAttribute("listaFotos", listaFotos);
                request.getRequestDispatcher("/WEB-INF/editarPublicaciones.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace(System.err);
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
