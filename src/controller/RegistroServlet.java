package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oscar on 25/03/17.
 */
@WebServlet(name = "RegistroServlet")
public class RegistroServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, String> errores = new HashMap<String, String>(); //Para recopilar los errores
        HashMap <String, String> correcto = new HashMap<String, String>(); //Para recopilar los datos bien introducidos
        boolean error=false;
        String email= (request.getParameter("registroEmail")).trim();

        //TODO Verificar email en la BD

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
            errores.put("nombreEmpresa","Introduzca el nombre de la Empresa");
            error=true;
        }
        else{
            correcto.put("nombreEmpresa",nick);
        }

        String contrasenya = request.getParameter("registroContrasenya");
        if(contrasenya==null || contrasenya.equals(new String(""))){
            errores.put("contrasenya","Escriba la contrase&ntilde;a");
            error=true;
        }
        else{
            correcto.put("contrasenya",contrasenya);
        }

        //TODO Unir servlet con Facade cuando esté creado
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
            request.getRequestDispatcher("registroVendedor.jsp").forward(request,response);
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


}
