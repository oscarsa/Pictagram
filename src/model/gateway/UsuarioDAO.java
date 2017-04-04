package model.gateway;

import model.data.UsuarioVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oscar on 28/03/17.
 */
public class UsuarioDAO {
    private static Connection con;

    public UsuarioDAO(Connection conexion) {
        con=conexion;
    }

    public boolean existeNick(String nick) {
        String sql;
        sql = "SELECT nickname FROM Usuarios WHERE nickname = ?";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            ResultSet rset = pstm.executeQuery();

            if (rset.next()) {
                //Nick encontrado
                return true;
            } else {
                //Nick no encontrado
                return false;
            }

        }  catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registrarUsuario(String nick, String email, String pass) {
        String sql;
        sql = "INSERT INTO Usuarios (nickname,email,contra) VALUES (?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            pstm.setString(2,email);
            pstm.setString(3,pass);
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
        }
    }
}
