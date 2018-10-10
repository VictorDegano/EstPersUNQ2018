package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

public class Turno {

    public String comentario;
    public int dmg;


    public Turno(String comentario, int dmg) {
        this.comentario = comentario;
        this.dmg = dmg;
    }


    public void setComentario(String comentarios){
        this.comentario= comentarios;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }


}
