package model;

/**
 * Created by oscar on 18/05/17.
 */
public class RelacionVO {
    private int idRelacion;
    private String nick_envia_pet;
    private String nick_recibe_pet;

    public RelacionVO(int idRelacion, String nick_envia_pet, String nick_recibe_pet) {
        this.idRelacion = idRelacion;
        this.nick_envia_pet = nick_envia_pet;
        this.nick_recibe_pet = nick_recibe_pet;
    }

    public int getIdRelacion() {
        return idRelacion;
    }

    public String getNick_envia_pet() {
        return nick_envia_pet;
    }

    public String getNick_recibe_pet() {
        return nick_recibe_pet;
    }
}
