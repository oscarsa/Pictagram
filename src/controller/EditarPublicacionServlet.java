package controller;

import model.FotoDAO;
import model.FotoVO;
import model.UsuarioDAO;

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

/**
 * Created by oscar on 17/05/17.
 */
@WebServlet(name = "EditarPublicacionServlet")
public class EditarPublicacionServlet extends HttpServlet {

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

        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        String nick="";

        String idFoto = request.getParameter("idPub");

        FotoDAO datosFoto = new FotoDAO(ds);
        FotoVO objetoFoto = datosFoto.obtenerFoto(Integer.parseInt(idFoto));

        String rutaFoto = objetoFoto.getFoto();
        request.setAttribute("rutaFoto",rutaFoto);
        request.setAttribute("idFoto",idFoto);

        boolean errorTitulo=false;
        String titulo = request.getParameter("titulo");

        if(titulo.equals("")){
            titulo = "";
            errorTitulo=true;
        }

        boolean errorDescripcion = false;
        String descripcion = request.getParameter("descripcion");

        if(descripcion.equals("")){
            descripcion="";
            errorDescripcion=true;
        }

        if (session != null && session.getAttribute("usuario") != null) {
            nick = (String) session.getAttribute("usuario");
            //out.println("Sesión iniciada, nick: " + nick);
        } else {
            //out.println("Sesión no iniciada!");
            response.sendRedirect("index.jsp");
        }

        FotoDAO fotoDAO = new FotoDAO(ds);
        FotoVO fotoVO = fotoDAO.obtenerFoto(Integer.parseInt(idFoto));


        if(errorTitulo && errorDescripcion) {
            //Se deja como estaba
            String tituloValido = fotoVO.getTitulo();
            String descripcionValida = fotoVO.getDescripcion();
            request.setAttribute("titulo", tituloValido);
            request.setAttribute("descripcion", descripcionValida);
            request.setAttribute("tituloModificadoFail",true);
            request.setAttribute("descripcionModificadaFail",true);
            request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);

        } else if (errorTitulo) {
            //Se modifica solo descripción
            boolean actualizada = false;
            boolean descripcionActualizada = false;

            if(fotoVO.getDescripcion().compareTo(descripcion)!=0) {
                //Descripción modificada, actualizamos BD
                descripcionActualizada = fotoDAO.nuevaDescripcion(Integer.parseInt(idFoto),descripcion);
                actualizada = true;
            }

            String tituloValido = fotoVO.getTitulo();

            if(actualizada && descripcionActualizada) {
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("tituloModificadoFail",true);
                request.setAttribute("descripcionModificadaOk",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            }  else if(!actualizada) {
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("tituloModificadoFail",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            } else {
                out.println("Problema al modificar descripción");
            }

        } else if (errorDescripcion) {
            //Se modifica solo título

            boolean actualizado = false;
            boolean tituloActualizado = false;

            if(fotoVO.getTitulo().compareTo(titulo)!=0) {
                //Titulo modificado, actualizamos BD
                tituloActualizado = fotoDAO.nuevoTitulo(Integer.parseInt(idFoto),titulo);
                actualizado = true;
            }

            String descripcionValida = fotoVO.getDescripcion();

            if(actualizado && tituloActualizado) {
                request.setAttribute("titulo", titulo);
                request.setAttribute("descripcion", descripcionValida);
                request.setAttribute("tituloModificadoOk",true);
                request.setAttribute("descripcionModificadaFail",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            }  else if(!actualizado) {
                request.setAttribute("titulo", titulo);
                request.setAttribute("descripcion", descripcionValida);
                request.setAttribute("descripcionModificadaFail",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            } else {
                out.println("Problema al modificar título");
            }


        } else {
            //Se modifica todoo
            boolean intentaTitulo = false;
            boolean intentaDescripcion = false;
            boolean tituloActualizado = false;
            boolean descripcionActualizada = false;

            String tituloValido = fotoVO.getTitulo();
            String descripcionValida = fotoVO.getDescripcion();

            if(fotoVO.getTitulo().compareTo(titulo)!=0) {
                //Titulo modificado, actualizamos BD
                tituloActualizado = fotoDAO.nuevoTitulo(Integer.parseInt(idFoto),titulo);
                intentaTitulo = true;
            }

            if(fotoVO.getDescripcion().compareTo(descripcion)!=0) {
                //Descripción modificada, actualizamos BD
                descripcionActualizada = fotoDAO.nuevaDescripcion(Integer.parseInt(idFoto),descripcion);
                intentaDescripcion = true;
            }

            if(tituloActualizado && descripcionActualizada) {
                //Titulo y descripción actualizados ok
                request.setAttribute("titulo", titulo);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("tituloModificadoOk",true);
                request.setAttribute("descripcionModificadaOk",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);

            } else if (intentaDescripcion && tituloActualizado) {
                //Titulo actualizado, descripcion falla
                request.setAttribute("titulo", titulo);
                request.setAttribute("descripcion", descripcionValida);
                request.setAttribute("tituloModificadoOk",true);
                request.setAttribute("descripcionModificadaFail",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);

            } else if (intentaTitulo && descripcionActualizada) {
                //Descripcion actualizada, titulo falla
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("tituloModificadoFail",true);
                request.setAttribute("descripcionModificadaOk",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            } else if (!intentaDescripcion && tituloActualizado) {
                //Titulo actualizado, descripcion no se modifica
                request.setAttribute("titulo", titulo);
                request.setAttribute("descripcion", descripcionValida);
                request.setAttribute("tituloModificadoOk",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);

            } else if (!intentaTitulo && descripcionActualizada) {
                //Descripcion actualizada, titulo no se modifica
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("descripcionModificadaOk",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            } else if (intentaDescripcion && intentaTitulo) {
                //Intenta modificar todoo, falla todoo
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcionValida);
                request.setAttribute("tituloModificadoFail",true);
                request.setAttribute("descripcionModificadaFail",true);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            } else {
                //No se intenta modificar nada
                request.setAttribute("titulo", tituloValido);
                request.setAttribute("descripcion", descripcionValida);
                request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request, response);
            }

        }

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
            String idPublicacion= (String)request.getParameter("idPub");
            FotoDAO fotoDAO = new FotoDAO(ds);
            FotoVO foto = fotoDAO.obtenerFoto(Integer.parseInt(idPublicacion));
            String titulo = foto.getTitulo();
            String descripcion = foto.getDescripcion();
            String rutaFoto = foto.getFoto();

            request.setAttribute("idFoto",idPublicacion);
            request.setAttribute("nick",nick);
            request.setAttribute("titulo",titulo);
            request.setAttribute("descripcion",descripcion);
            request.setAttribute("rutaFoto",rutaFoto);
            request.getRequestDispatcher("/WEB-INF/editarPublicacion.jsp").forward(request,response);

        }
    }
}
