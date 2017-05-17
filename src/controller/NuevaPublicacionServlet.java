package controller;

import model.FotoDAO;
import model.FotoVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;


/**
 * Created by oscar on 7/05/17.
 */
@WebServlet(name = "NuevaPublicacionServlet")
@MultipartConfig
public class NuevaPublicacionServlet extends HttpServlet {

    File uploads = new File("/home/oscar/apache-tomcat-9.0.0.M18/webapps/imagenes");

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap <String, String> errores = new HashMap<String, String>(); //Para recopilar los errores
        HashMap<String, String> correcto = new HashMap<String, String>(); //Para recopilar los datos bien introducidos

        //Control de sesión de usuario
        HttpSession session = request.getSession(false);
        String nick = "";

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");

        } else {
            //out.println("Sesión no iniciada!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

        //Gestión de errores en los campos
        boolean error = false;

        //Comprobación de título
        String titulo = (request.getParameter("inputTitulo")).trim();
        if(titulo.equals("")) {
            errores.put("titulo","Introduzca el título de la publicación");
            error = true;
        } else {
            correcto.put("titulo",titulo);
        }

        //Comprobación de descripción
        String descripcion = (request.getParameter("inputDescripcion")).trim();
        if(descripcion.equals("")) {
            errores.put("descripcion","Introduzca la descripción de la publicación");
            error = true;
        } else {
            correcto.put("descripcion",descripcion);
        }

        //Comprobación de imagen
        //Procesamos los datos de la imagen de la publicación
        //+info -> http://stackoverflow.com/a/2424824
        Part imagenPart = request.getPart("inputFoto");
        if (imagenPart == null) {
            error = true;
        }

        String imagenName = Paths.get(imagenPart.getSubmittedFileName()).getFileName().toString();//InputStream imagenContent = imagenPart.getInputStream();

        if(imagenName == null || imagenName.equals("")) {
            errores.put("imagen","Vuelva a introducir una fotografía válida");
            error = true;
        } else {
            correcto.put("imagen",imagenName);
        }

        PrintWriter out = response.getWriter();

        //Gestión de datos recibidos
        out.println("Errores: "+error);

        if(!error) {

            File file = File.createTempFile(imagenName, ".jpg", uploads);

            try (InputStream imagenContent = imagenPart.getInputStream()) {
                Files.copy(imagenContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            FotoVO fotoVO = new FotoVO(nick,titulo,file.getName(),descripcion);

            FotoDAO fotoDAO = new FotoDAO(ds);


            try {
                boolean todoCorrecto = fotoDAO.nuevaPublicacion(fotoVO);

                if(todoCorrecto) {
                    request.getRequestDispatcher("portada.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
                out.println(displayErrorForWeb(e));
            }

        } else{
            request.setAttribute("errores",errores);
            request.setAttribute("correcto",correcto);
            request.getRequestDispatcher("/WEB-INF/nuevaPublicacion.jsp").forward(request, response);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String nick = "";

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
