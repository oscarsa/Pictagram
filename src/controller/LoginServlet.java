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
            // nombre del recurso en el context.xml
            ds = (DataSource) env.lookup("jdbc/ConexionMySQL");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //response.sendRedirect("ok.html");

        PrintWriter out = response.getWriter();

        String sql = "SELECT * FROM Usuarios";
        try {
            con = ds.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rset = pstm.executeQuery();
            System.out.println("NICK | EMAIL | CONTRASEÑA ");
            while (rset.next()) {

                System.out.println(rset.getInt(1)+" | "+rset.getString(2)+" | "+rset.getString(3));
            }

            //response.sendRedirect("ok.html");

        } catch (SQLException e) {
            //response.sendRedirect("error.html");
            e.printStackTrace(System.err);
            out.println("Stack Trace:<br/>");
            out.println("<br/><br/>Stack Trace (for web display):</br>");
            out.println(displayErrorForWeb(e));
        }


/*
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
        String messaggeError = "";







        //TODO Unir servlet con Facade cuando esté creado
        /*
        CompradorVO c = new CompradorVO();
        VendedorVO v = new VendedorVO();
        int usuario = 0;
        int id=0;
        String tipo = "";
        if(!error){
            try{
                MarketplaceFacade fachada = new MarketplaceFacade();
                c = fachada.loginComprador(email,contrasenya);
                usuario=1;
                id=c.getid();
                tipo="comprador";
            }catch (Exception e){
                try{
                    MarketplaceFacade fachada = new MarketplaceFacade();
                    v = fachada.loginVendedor(email,contrasenya);
                    usuario=2;
                    id=v.getid();
                    tipo = "vendedor";
                }catch (Exception e1){
                    e1.printStackTrace(System.err);
                }
            }
        }
        if(usuario==0){
            messaggeError= "Los datos del usuario son incorrectos";
            request.setAttribute("error",error);
            request.setAttribute("messaggeError",messaggeError);
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        else{
            response.sendRedirect("modificarComprador.jsp?id="+id+"&tipo="+tipo);
        }

        */
    }

    public static boolean validateEmail(String email) {
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public String displayErrorForWeb(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String stackTrace = sw.toString();
        return stackTrace.replace(System.getProperty("line.separator"), "<br/>\n");
    }
}
