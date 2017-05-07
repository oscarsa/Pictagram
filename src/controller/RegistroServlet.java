package controller;

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
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscar on 25/03/17.
 */
@WebServlet(name = "RegistroServlet")
public class RegistroServlet extends HttpServlet {

    private DataSource ds;

    //TODO esto va a los DAO y compartido en otra clase entre todos
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

        HashMap<String, String> errores = new HashMap<>(); //Para recopilar los errores
        HashMap<String, String> correcto = new HashMap<>(); //Para recopilar los data bien introducidos
        String email, nick, contrasenya;
        boolean error = false;
        PrintWriter out = response.getWriter();

        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        //Valida email
        email = (request.getParameter("registroEmail")).trim();
        if (email == null || email.equals("")) {
            errores.put("email", "Introduzca email");
            error = true;
        } else if (!validateEmail(email)) {
            errores.put("email", "Email err&#243neo");
            error = true;
        } else {
            correcto.put("email", email);
        }

        //Valida nick
        nick = (request.getParameter("registroNick")).trim();
        if (nick == null || nick.equals("")) {
            errores.put("nick", "Introduzca el nick");
            error = true;
        } else {
            correcto.put("nick", nick);
        }

        //Valida contraseña
        contrasenya = request.getParameter("registroContrasenya");
        if (contrasenya == null || contrasenya.equals("")) {
            errores.put("contrasenya", "Escriba la contrase&ntilde;a");
            error = true;
        } else {
            correcto.put("contrasenya", contrasenya);
        }

        //Procedemos a intentar el registro si no ha habido errores
        if (!error) {
            try {
                UsuarioDAO usuarioDAO = new UsuarioDAO(ds);

                if (usuarioDAO.existeNick(nick)) {
                    //Nick existe, no se puede registrar
                    out.println("Nick: " + nick + " - Nick ya existente, pruebe con otro");
                    errores.put("abrirRegistro", "El nick utilizado ya se encuentra registrado, por favor elija uno " +
                            "diferente");
                    request.setAttribute("errores",errores);
                    request.setAttribute("correcto",correcto);
                    request.getRequestDispatcher("index.jsp").forward(request,response);
                } else {
                    //Nick no existe
                    out.println("Nick: " + nick + " - Procedemos al registro...");
                    boolean registroCorrecto = usuarioDAO.registrarUsuario(nick, email, contrasenya);
                    if (registroCorrecto) {
                        out.println("...el registro se ha realizado CORRECTAMENTE!");

                    } else {
                        //out.println("...el registro ha sido erróneo");
                        errores.put("abrirRegistro", "Error, no se ha podido realizar el registro");
                        request.setAttribute("errores",errores);
                        request.setAttribute("correcto",correcto);
                        request.getRequestDispatcher("index.jsp").forward(request,response);
                    }
                }
            } catch (SQLException e) {
                //e.printStackTrace();
                out.println("ERROR");
            }
        } else {
            errores.put("abrirRegistro", "Uno o más campos son inválidos");
            //Algún campo no es correcto
            //out.println("Algún campo no es correcto");
            request.setAttribute("errores",errores);
            request.setAttribute("correcto",correcto);
            request.getRequestDispatcher("index.jsp").forward(request,response);
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
