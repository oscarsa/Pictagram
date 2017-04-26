package controller;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscar on 25/04/17.
 */
@WebServlet(name = "EditarUsuarioServlet")
public class EditarUsuarioServlet extends HttpServlet {

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

    //Utilizado al inicio
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        //Para indicar si hay error
        String messaggeError = "Este es el mensaje de error que se le mostrará al usuario";
        response.setContentType("text/html");

        boolean error = false;
        HttpSession session = request.getSession(false);

        String nick="";

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
            String email = usuarioDAO.obtenerEmail(nick);

            //UsuarioVO usuarioVO = new UsuarioVO(nick,email);

            request.setAttribute("nick",nick);
            request.setAttribute("email",email);
            request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);

        }
    }

    //Utilizado tras pulsar el botón 'Modificar perfil'
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        String nick="";

        boolean eliminar = false;
        if (request.getParameter("eliminar") != null){
            //Botón eliminar
            if (session != null && session.getAttribute("usuario") != null) {
                nick = (String) session.getAttribute("usuario");
                //out.println("Sesión iniciada, nick: " + nick);
            } else {
                //out.println("Sesión no iniciada!");
                response.sendRedirect("index.jsp");
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(ds);

            boolean eliminacionCorrecta = usuarioDAO.eliminarUsuario(nick);

            if(eliminacionCorrecta) {
                response.sendRedirect("index.jsp");
            } else {
                String emailValido = usuarioDAO.obtenerEmail(nick);
                request.setAttribute("nick", nick);
                request.setAttribute("email", emailValido);
                request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request, response);
            }

        } else {
            //Botón modificar

            boolean errorEmail=false;
            String email = (request.getParameter("email")).trim();

            if(email==null || email.equals(new String("")) || !validateEmail(email)){
                email = "";
                errorEmail=true;
            }

            boolean passwordVacia = false;
            String contrasenya = (request.getParameter("password")).trim();

            if(contrasenya==null || contrasenya.equals(new String(""))){
                contrasenya="";
                passwordVacia=true;
            }

            //Para indicar si hay error
            String messaggeError = "Este es el mensaje de error que se le mostrará al usuario";





            if (session != null && session.getAttribute("usuario") != null) {
                nick = (String) session.getAttribute("usuario");
                //out.println("Sesión iniciada, nick: " + nick);
            } else {
                //out.println("Sesión no iniciada!");
                response.sendRedirect("index.jsp");
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO(ds);

            if(errorEmail) {
                String emailValido = usuarioDAO.obtenerEmail(nick);
                request.setAttribute("nick", nick);
                request.setAttribute("email", emailValido);
                request.setAttribute("emailModificadoFail", true);
                request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request, response);
            } else if(passwordVacia) {
                //Solo actualizará el email
                boolean actualizado = false;
                boolean emailActualizado = false;

                if(usuarioDAO.obtenerEmail(nick).compareTo(email) != 0) {
                    //Email modificado, actualizamos en BD
                    emailActualizado = usuarioDAO.nuevoEmail(nick,email);
                    actualizado = true;
                }

                if(actualizado && emailActualizado) {
                    request.setAttribute("nick",nick);
                    request.setAttribute("email",email);
                    request.setAttribute("emailModificadoOk", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                } else if(!actualizado) {
                    request.setAttribute("nick",nick);
                    request.setAttribute("email",email);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                } else {
                    out.println("Problema al modificar email");
                }
            }
            else{
                //Actualizará email y contraseña

                //Solo actualizará el email
                boolean actualizado = false;
                boolean emailActualizado = false;

                if(usuarioDAO.obtenerEmail(nick).compareTo(email) != 0) {
                    //Email modificado, actualizamos en BD
                    emailActualizado = usuarioDAO.nuevoEmail(nick,email);
                    actualizado = true;
                }

                boolean passActualizada =false;
                if(contrasenya.length()<10) {
                    passActualizada=usuarioDAO.nuevoPassword(nick,contrasenya);
                }


                if(actualizado && emailActualizado && passActualizada) {
                    //Email y contraseña actualizados correctamente
                    request.setAttribute("nick",nick);
                    request.setAttribute("password",contrasenya);
                    request.setAttribute("email",email);
                    request.setAttribute("emailModificadoOk", true);
                    request.setAttribute("passModificadaOk", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                } else if (actualizado && emailActualizado) {
                    //Email actualizado correctamente, contraseña NO
                    request.setAttribute("nick",nick);
                    request.setAttribute("email",email);
                    request.setAttribute("emailModificadoOk", true);
                    request.setAttribute("passModificadaFail", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                } else if (actualizado && !emailActualizado && passActualizada) {
                    //Contraseña actualizada correctamente, email NO
                    String emailValido = usuarioDAO.obtenerEmail(nick);
                    request.setAttribute("nick",nick);
                    request.setAttribute("password",contrasenya);
                    request.setAttribute("email",emailValido);
                    request.setAttribute("emailModificadoFail", true);
                    request.setAttribute("passModificadaOk", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                }
                else if (!actualizado && passActualizada) {
                    //Contraseña actualizada correctamente, email NO
                    request.setAttribute("nick",nick);
                    request.setAttribute("password",contrasenya);
                    request.setAttribute("email",email);
                    request.setAttribute("passModificadaOk", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                } else if(!actualizado){
                    request.setAttribute("nick",nick);
                    request.setAttribute("email",email);
                    request.setAttribute("passModificadaFail", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                }
                else {
                    String emailValido = usuarioDAO.obtenerEmail(nick);
                    request.setAttribute("nick",nick);
                    request.setAttribute("email",emailValido);
                    request.setAttribute("passModificadaFail", true);
                    request.setAttribute("emailModificadoFail", true);
                    request.getRequestDispatcher("/WEB-INF/editarUsuario.jsp").forward(request,response);
                }

            }

        }

    }

    private static boolean validateEmail(String email) {
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
