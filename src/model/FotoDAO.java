package model;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oscar on 7/05/17.
 */
public class FotoDAO {
    private static Connection con;
    private DataSource ds;
    private PreparedStatement pstm;
    private ResultSet rset;


    public FotoDAO(DataSource ds) {
        this.ds = ds;
    }

    //TODO Modificar para que solo devuelva las fotos de las amistades, no todas
    public ArrayList <FotoVO> fotosDeAmigos(String miNickname) {
        ArrayList <FotoVO> listaFotos = new ArrayList<>();
        FotoVO foto;

        //TODO Modificar la consulta: where nickname=miNickname
        String sql = "SELECT * FROM Fotos";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                //Se crea un objeto FotoVO con cada una de las fotos recuperadas
                foto = new FotoVO(
                        rset.getInt("idFoto"),
                        rset.getString("nickname"),
                        rset.getString("titulo"),
                        rset.getString("foto"),
                        rset.getString("descripcion"),
                        rset.getInt("valoracion"));

                //Se inserta el objeto en el ArrayList
                listaFotos.add(foto);
            }

            return listaFotos;

        } catch (SQLException e) {
            e.printStackTrace();
            return listaFotos;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }


}
