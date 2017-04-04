package controller;

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

    private Connection con;
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

        //response.sendRedirect("ok.html");

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

        //TODO Falta devolver errores y campos inválidos cuando el usuario hace login
        //Para indicar si hay error
        String messaggeError = "";


        if(!error)
        {
            out.println("Email: "+email);
            out.println("Contraseña: "+contrasenya);
            boolean loginCorrecto = login(email,contrasenya);
            if(loginCorrecto) {
                out.println("El login ha sido CORRECTO");
            } else {
                out.println("El login ha sido INCORRECTO");
            }
        }
        else
        {
            out.println("ERROR, campo no válido");
            out.println("Email: "+email);
            out.println("Contraseña: "+contrasenya);
        }


    }

    private boolean login(String email, String pass)
    {


       String sql = "SELECT nickname, email, contra " +
                "FROM Usuarios WHERE email ='"+email+"' AND contra ='"+pass+"'";

       try
       {
           con = ds.getConnection();
           PreparedStatement pstm = con.prepareStatement(sql);
           ResultSet rset = pstm.executeQuery();

           if (rset.next()) {
               //Login válido
               return true;
           } else {
               //Login inválido
               return false;
           }

       }
       catch (SQLException e)
       {
           e.printStackTrace();
           return true;

       }
        /*
        sql = "SELECT  FROM Usuarios";
        try {
            con = ds.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rset = pstm.executeQuery();
            System.out.println("NICK | EMAIL | CONTRASEÑA ");
            while (rset.next()) {

                System.out.println(rset.getString(1)+" | "+rset.getString(2)+" | "+rset.getString(3));
                System.out.println(rset.getString(1));
                System.out.println(rset.getString(2));
                System.out.println(rset.getString(3));
            }

            //response.sendRedirect("ok.html");

        } catch (SQLException e) {
            //response.sendRedirect("error.html");
            e.printStackTrace(System.err);
            System.out.println("Stack Trace:<br/>");
            System.out.println("<br/><br/>Stack Trace (for web display):</br>");
            System.out.println(displayErrorForWeb(e));
        }
        */
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
