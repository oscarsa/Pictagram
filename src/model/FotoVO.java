package model;

/**
 * Created by oscar on 7/05/17.
 */
public class FotoVO {

    private int idFoto;
    private String nickname;
    private String titulo;
    private String foto;
    private String descripcion;
    private int valoracion;

    public FotoVO(){}

    public FotoVO(int idFoto, String nickname, String titulo, String foto, String descripcion, int valoracion) {
        this.idFoto = idFoto;
        this.nickname = nickname;
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
        this.valoracion = valoracion;
    }

    public FotoVO(String nickname, String titulo, String foto, String descripcion) {
        this.nickname = nickname;
        this.titulo = titulo;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
