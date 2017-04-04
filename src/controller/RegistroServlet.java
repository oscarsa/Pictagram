package controller;

import model.data.UsuarioVO;
import model.gateway.UsuarioDAO;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscar on 25/03/17.
 */
@WebServlet(name = "RegistroServlet")
public class RegistroServlet extends HttpServlet {

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

        HashMap<String, String> errores = new HashMap<String, String>(); //Para recopilar los errores
        HashMap <String, String> correcto = new HashMap<String, String>(); //Para recopilar los data bien introducidos

        //Establece codificación para los data recibidos, para procesar bien carácteres especiales
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        boolean error=false;
        String email= (request.getParameter("registroEmail")).trim();


        if(email==null ||email.equals(new String(""))){
            errores.put("email","Introduzca email");
            error=true;
        }
        else if(!validateEmail(email)){
            errores.put("email","Email err&#243neo");
            error=true;
        }
        else{
            correcto.put("email",email);
        }

        //TODO Verificar nick en la BD

        String nick = (request.getParameter("registroNick")).trim();
        if(nick==null || nick.equals(new String(""))){
            errores.put("nick","Introduzca el nick");
            error=true;
        }
        else{
            correcto.put("nick",nick);
        }

        String contrasenya = request.getParameter("registroContrasenya");
        if(contrasenya==null || contrasenya.equals(new String(""))){
            errores.put("contrasenya","Escriba la contrase&ntilde;a");
            error=true;
        }
        else{
            correcto.put("contrasenya",contrasenya);
        }

        //Procedemos a intentar el registro si no ha habido errores
        if(!error) {
            try {
                con = ds.getConnection();
                UsuarioDAO usuarioDAO = new UsuarioDAO(con);

                if(usuarioDAO.existeNick(nick)) {
                    //Nick existe, no se puede registrar
                    out.println("Nick: "+nick+" - Nick ya existente, pruebe con otro");
                } else {
                    //Nick no existe
                    out.println("Nick: "+nick+" - Procedemos al registro...");
                    boolean registroCorrecto =  usuarioDAO.registrarUsuario(nick,email,contrasenya);
                    if(registroCorrecto) {
                        out.println("...el registro se ha realizado CORRECTAMENTE!");
                    } else {
                        out.println("...el registro ha sido erróneo");
                    }
                }


            }  catch (SQLException e)
            {
                //e.printStackTrace();
                out.println("ERROR");

            }
        } else {
            //Algún campo no es correcto
            out.println("Algún campo no es correcto");
        }





/*
        if (!error){

            VendedorVO v = new VendedorVO(nombreEmpresa,cif,email,direccion,Integer.parseInt(telefono),contrasenya);

            //Para mostrar errores correctamente
            PrintWriter out = response.getWriter();

            try{
                MarketplaceFacade fachada = new MarketplaceFacade();
                fachada.crearVendedor(v);
                response.sendRedirect("exito.html");
            }catch (Exception e){
                e.printStackTrace(System.err);
                out.println("Stack Trace:<br/>");
                out.println("<br/><br/>Stack Trace (for web display):</br>");
                out.println(displayErrorForWeb(e));
            }

        }
        else{
            request.setAttribute("errores",errores);
            request.setAttribute("correcto",correcto);
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
*/
    }
/*
    private boolean nickValido(String nick) {
        String sql = "SELECT nickname WHERE nickname = '"+nick+"'";

        try {
            con = ds.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rset = pstm.executeQuery();

            if (rset.next()) {
                //Nick encontrado, no es posible el nuevo registro con ese nick
                return false;
            } else {
                //Nick no encontrado, es posible registrar el nuevo nick
                return true;
            }
        }  catch (SQLException e)
        {
            e.printStackTrace();
            return true;
        }
    }
*/

    public static boolean validateEmail(String email) {

        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
