package model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oscar on 18/05/17.
 */
public class RelacionDAO {

    private static Connection con;
    private DataSource ds;
    private PreparedStatement pstm;
    private ResultSet rset;


    public RelacionDAO(DataSource ds) {
        this.ds = ds;
    }

    public ArrayList <RelacionVO> obtenerRelaciones(String nick) {

        ArrayList <RelacionVO> relaciones = new ArrayList<>();
        RelacionVO relacion;

        String sql = "SELECT * FROM Relacion WHERE nick_envia_pet=? OR nick_recibe_pet=?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nick);
            pstm.setString(2,nick);
            rset = pstm.executeQuery();

            while (rset.next()) {
                //Se crea un objeto RelacionVO con cada una de las relaciones recuperadas
                relacion = new RelacionVO(
                        rset.getInt("idRelacion"),
                        rset.getString("nick_envia_pet"),
                        rset.getString("nick_recibe_pet"));

                //Se inserta el objeto en el ArrayList
                relaciones.add(relacion);
            }

            return relaciones;

        } catch (SQLException e) {
            e.printStackTrace();
            return relaciones;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }
}
