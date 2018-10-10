package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Turno {

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    public String comentario;
    public int dmg;

/*[--------]Constructors[--------]*/
    public Turno() {}

    public Turno(String comentario, int dmg)
    {
        this.comentario = comentario;
        this.dmg = dmg;
    }

/*[--------]Getters & Setters[--------]*/
    public void setComentario(String comentarios){
        this.comentario= comentarios;
    }
    public String getComentario() { return comentario;  }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public int getDmg() {   return dmg; }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }
}
