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

            String nickAutorFoto = "";
            RelacionDAO relacionDAO = new RelacionDAO(ds);

            while (rset.next()) {

                nickAutorFoto = rset.getString("nickname");
                if(relacionDAO.tienenRelacion(miNickname,nickAutorFoto)) {
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

    public ArrayList <FotoVO> misFotos(String miNickname) {
        ArrayList <FotoVO> listaFotos = new ArrayList<>();
        FotoVO foto;

        //TODO Modificar la consulta: where nickname=miNickname
        String sql = "SELECT * FROM Fotos WHERE nickname=?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,miNickname);
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

    public FotoVO obtenerFoto(int idFoto) {

        String sql = "SELECT * FROM Fotos WHERE idFoto=?";

        FotoVO foto = new FotoVO();

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setInt(1,idFoto);
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

            }

            return foto;

        } catch (SQLException e) {
            e.printStackTrace();
            return foto;

        } finally {
            //Aseguramos que cerramos todos objetos relacionados con la conexión a la BD
            try { rset.close(); } catch (Exception e) { /* ignored */ }
            try { pstm.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    public boolean nuevoTitulo(int idFoto, String nuevoTitulo) {
        String sql = "UPDATE Fotos SET titulo = ? WHERE idFoto = ?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nuevoTitulo);
            pstm.setInt(2,idFoto);

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

    public boolean nuevaDescripcion(int idFoto, String nuevaDescripcion) {
        String sql = "UPDATE Fotos SET descripcion = ? WHERE idFoto = ?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,nuevaDescripcion);
            pstm.setInt(2,idFoto);

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

    public boolean nuevaPublicacion(FotoVO foto) {
        String sql = "INSERT INTO Fotos (nickname,titulo,foto,valoracion,descripcion) VALUES (?,?,?,?,?)";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setString(1,foto.getNickname());
            pstm.setString(2,foto.getTitulo());
            pstm.setString(3,foto.getFoto());
            pstm.setInt(4,3);
            pstm.setString(5,foto.getDescripcion());
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

    //TODO Falta eliminar la imagen del directorio del servidor
    public boolean eliminarPublicacion(int idPublicacion) {
        String sql = "DELETE FROM Fotos WHERE idFoto=?";

        try {
            con = ds.getConnection();
            pstm = con.prepareStatement(sql);
            pstm.setInt(1,idPublicacion);

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
