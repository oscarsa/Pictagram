package model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Creado por Óscar Saboya e Ían Ávila el 18/05/17.
 */
public class RelacionDAO {

    private static Connection con;
    private DataSource ds;
    private PreparedStatement pstm;
    private PreparedStatement pstm1;
    private PreparedStatement pstm2;
    private ResultSet rset;
    private ResultSet rset1;
    private ResultSet rset2;


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

    public boolean tienenRelacion(String nick1, String nick2) {

        String sql1 = "SELECT * FROM Relacion WHERE nick_envia_pet=? AND nick_recibe_pet=?";
        String sql2 = "SELECT * FROM Relacion WHERE nick_envia_pet=? AND nick_recibe_pet=?";

        try {
            con = ds.getConnection();

            pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1,nick1);
            pstm1.setString(2,nick2);

            pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1,nick2);
            pstm2.setString(2,nick1);

            rset1 = pstm1.executeQuery();


            if (rset1.next()) {
                return true;
            } else {
                rset2 = pstm2.executeQuery();
                if(rset2.next()){
                    return true;
                } else {
                    return false;
                }
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

    public boolean eliminarRelacion(String nick1, String nick2) {
        String sql1 = "SELECT * FROM Relacion WHERE nick_envia_pet=? AND nick_recibe_pet=?";
        String sql2 = "SELECT * FROM Relacion WHERE nick_envia_pet=? AND nick_recibe_pet=?";

        String sqlEliminar = "DELETE FROM Relacion WHERE nick_envia_pet=? AND nick_recibe_pet=?";
        int eliminado = 0;

        try {
            con = ds.getConnection();

            pstm1 = con.prepareStatement(sql1);
            pstm1.setString(1,nick1);
            pstm1.setString(2,nick2);

            pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1,nick2);
            pstm2.setString(2,nick1);

            rset1 = pstm1.executeQuery();


            if (rset1.next()) {
                pstm = con.prepareStatement(sqlEliminar);
                pstm.setString(1,nick1);
                pstm.setString(2,nick2);
                eliminado = pstm.executeUpdate();

                return eliminado>0;
            } else {
                rset2 = pstm2.executeQuery();
                if(rset2.next()){
                    pstm = con.prepareStatement(sqlEliminar);
                    pstm.setString(1,nick2);
                    pstm.setString(2,nick1);
                    eliminado = pstm.executeUpdate();

                    return eliminado>0;
                } else {
                    return false;
                }
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
}
