package model;

/**
 * Created by oscar on 28/03/17.
 */
public class UsuarioVO {
    private String nick;
    private String email;
    private String contrasenya;

    public UsuarioVO(){}
    public UsuarioVO(String nick, String email, String contrasenya) {
        this.nick = nick;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setContrasenya(String contrasenya)
    {
        this.contrasenya = contrasenya;
    }

    public String getNick()
    {
        return nick;
    }

    public String getEmail()
    {
        return email;
    }

}
