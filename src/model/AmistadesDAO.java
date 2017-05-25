package model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oscar on 25/05/17.
 */
public class AmistadesDAO {

    private static Connection con;
    private DataSource ds;
    private PreparedStatement pstm;
    private ResultSet rset;

    public AmistadesDAO(DataSource ds) {
        this.ds = ds;
    }

    public boolean haMandadoPeticion(String nickManda, String nickRecibe) {

        String sql = "SELECT * FROM Amistades WHERE nickname=? AND Amigo=?";


        try {
            con = ds.getConnection();

            pstm = con.prepareStatement(sql);
            pstm.setString(1,nickManda);
            pstm.setString(2,nickRecibe);

            rset = pstm.executeQuery();

            if (rset.next()) {
                return true;
            } else {
               return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean confirmarPeticion(String nickManda, String nickRecibe) {

        String sql = "DELETE FROM Amistades WHERE nickname=? AND Amigo=?";
        String sqlInsert = "INSERT INTO Relacion (nick_envia_pet,nick_recibe_pet) VALUES (?,?)";
        try {
            con = ds.getConnection();

            pstm = con.prepareStatement(sql);
            pstm.setString(1,nickManda);
            pstm.setString(2,nickRecibe);

            int confirmado = pstm.executeUpdate();

            pstm = con.prepareStatement(sqlInsert);
            pstm.setString(1,nickManda);
            pstm.setString(2,nickRecibe);

            int resultadoInsert = pstm.executeUpdate();

            return confirmado>0 && resultadoInsert>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean enviarPeticion(String nickManda, String nickRecibe) {

        String sql = "INSERT INTO Amistades (nickname,Amigo) VALUES (?,?)";

        try {
            con = ds.getConnection();

            pstm = con.prepareStatement(sql);
            pstm.setString(1,nickManda);
            pstm.setString(2,nickRecibe);

            int resultadoInsert = pstm.executeUpdate();

            return resultadoInsert>0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public ArrayList<AmistadesVO> obtenerPeticionesDeAmistad(String nick) {
        ArrayList <AmistadesVO> amistades = new ArrayList<>();
        AmistadesVO amistad;

        String sql = "SELECT * FROM Amistades WHERE Amigo=?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            rset = pstm.executeQuery();

            while (rset.next()) {

                amistad = new AmistadesVO(
                        rset.getInt("idAmistades"),
                        rset.getString("nickname"),
                        rset.getString("Amigo"));

                //Se inserta el objeto en el ArrayList
                amistades.add(amistad);
            }

            return amistades;

        } catch (SQLException e) {
            e.printStackTrace();
            return amistades;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }


}
