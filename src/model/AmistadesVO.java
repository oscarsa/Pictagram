package model;

/**
 * Creado por Óscar Saboya e Ían Ávila el 25/05/17.
 */
public class AmistadesVO {
    private int idAmistades;
    private String nickname;
    private String Amigo;


    public AmistadesVO(int idAmistades, String nickname, String amigo) {
        this.idAmistades = idAmistades;
        this.nickname = nickname;
        Amigo = amigo;
    }


    public int getIdAmistades() {
        return idAmistades;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAmigo() {
        return Amigo;
    }
}
