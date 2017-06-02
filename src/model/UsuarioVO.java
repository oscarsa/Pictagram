package model;

/**
 * Creado por Óscar Saboya e Ían Ávila el 28/03/17.
 */
public class UsuarioVO {
    private String nick;
    private String email;

    public UsuarioVO(){}

    public UsuarioVO(String nick, String email) {
        this.nick = nick;
        this.email = email;
    }


    public String getNick() {
        return nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
