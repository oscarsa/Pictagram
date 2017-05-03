package model;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oscar on 28/03/17.
 */
public class UsuarioDAO {

    private static Connection con;
    private DataSource ds;
    private PreparedStatement pstm;
    private ResultSet rset;
    private static final String SALT = "texto.usado.como.sal";

    public UsuarioDAO(DataSource ds) {
        this.ds = ds;
    }

    public boolean existeNick(String nick) throws SQLException{

        String sql = "SELECT nickname FROM Usuarios WHERE nickname = ?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            rset = pstm.executeQuery();

            if (rset.next()) {
                //Nick encontrado
                return true;
            } else {
                //Nick no encontrado
                return false;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
        }

        return hash.toString();
    }

    public boolean registrarUsuario(String nick, String email, String pass) {

        String saltedPassword = SALT + pass;
        String hashedPassword = generateHash(saltedPassword);

        String sql = "INSERT INTO Usuarios (nickname,email,contra) VALUES (?,?,?)";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            pstm.setString(2,email);
            pstm.setString(3,hashedPassword);
            int resultadoInsert = pstm.executeUpdate();

            if (resultadoInsert>0) {
                //Registro correcto
                return true;
            } else {
                //Registro incorrecto
                return false;
            }

        }  catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean login(String nick, String pass) {

        String saltedPassword = SALT + pass;
        String hashedPassword = generateHash(saltedPassword);

        String sql = "SELECT nickname, email, contra FROM Usuarios WHERE nickname=? AND contra=?";

        try
        {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            pstm.setString(2,hashedPassword);
            rset = pstm.executeQuery();

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
            return false;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public String obtenerEmail(String nick) {
        String sql = "SELECT email FROM Usuarios WHERE nickname=?";

        try
        {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            rset = pstm.executeQuery();

            if (rset.next()) {
                return rset.getString("email");

            } else {
                return "";
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "";

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean nuevoEmail(String nick, String nuevoEmail) {

        String sql = "UPDATE Usuarios SET email = ? WHERE nickname = ?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nuevoEmail);
            pstm.setString(2,nick);

            int resultadoUpdate = pstm.executeUpdate();

            if (resultadoUpdate>0) {
                //Actualización correcta
                return true;
            } else {
                //Actualización incorrecta
                return false;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean nuevoPassword(String nick, String nuevoPassword) {

        String saltedPassword = SALT + nuevoPassword;
        String hashedPassword = generateHash(saltedPassword);

        String sql = "UPDATE Usuarios SET contra = ? WHERE nickname = ?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,hashedPassword);
            pstm.setString(2,nick);

            int resultadoUpdate = pstm.executeUpdate();

            if (resultadoUpdate>0) {
                //Actualización correcta
                return true;
            } else {
                //Actualización incorrecta
                return false;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean eliminarUsuario(String nick) {
        String sql = "DELETE FROM Usuarios WHERE nickname=?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);

            int resultadoDelete = pstm.executeUpdate();

            if (resultadoDelete>0) {
                //Eliminacion correcta
                return true;
            } else {
                //Eliminacion incorrecta
                return false;
            }

        }  catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }


}
