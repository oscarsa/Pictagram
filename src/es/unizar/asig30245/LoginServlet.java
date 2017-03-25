package es.unizar.asig30245;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
