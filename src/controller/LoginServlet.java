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
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

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

        boolean error=false;
        String email= (request.getParameter("loginEmail")).trim();


        if(email==null || email.equals(new String(""))){
            email="";
            error=true;
        }
        else if(!validateEmail(email)){
            email="";
            error=true;
        }

        String contrasenya = (request.getParameter("loginContrasenya")).trim();

        if(contrasenya==null || contrasenya.equals(new String(""))){
            contrasenya="";
            error=true;
        }

        //Para indicar si hay error
        String messaggeError = "Este es el mensaje de error que se le mostrará al usuario";

        if(error){
            messaggeError= "Email o contraseña inválidos, revise los datos introducidos";
            request.setAttribute("loginError",error);
            request.setAttribute("mensajeErrorLogin",messaggeError);
            request.setAttribute("abrirLogin",true);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
        else{
            //response.sendRedirect("modificarComprador.jsp?id="+id+"&tipo="+tipo);
            out.println("Email: "+email);
            out.println("Contraseña: "+contrasenya);

            UsuarioDAO usuarioDAO = new UsuarioDAO(ds);
            boolean loginCorrecto = usuarioDAO.login(email,contrasenya);
            if(loginCorrecto) {
                out.println("El login ha sido CORRECTO");
            } else {
                messaggeError= "Los datos de login son incorrectos, inténtelo de nuevo";
                request.setAttribute("loginError",error);
                request.setAttribute("mensajeErrorLogin",messaggeError);
                request.setAttribute("abrirLogin",true);
                request.getRequestDispatcher("index.jsp").forward(request,response);
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
