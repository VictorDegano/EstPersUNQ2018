package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Campeon
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Bicho bichoCampeon;

    private Timestamp fechaInicioDeCampeon;
    private Timestamp fechaFinDeCampeon;

    @ManyToOne
    private Dojo dojo;


/*[--------]Constructor[--------]*/
    public Campeon() {  }

/*[--------]Getters & Setters[--------]*/
    public Bicho getBichoCampeon() {    return bichoCampeon;    }
    public void setBichoCampeon(Bicho bichoCampeon) {   this.bichoCampeon = bichoCampeon;   }

    public Timestamp getFechaInicioDeCampeon() {    return fechaInicioDeCampeon;    }
    public void setFechaInicioDeCampeon(Timestamp fechaInicioDeCampeon) {   this.fechaInicioDeCampeon = fechaInicioDeCampeon;   }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }

    public Timestamp getFechaFinDeCampeon() {   return fechaFinDeCampeon;   }
    public void setFechaFinDeCampeon(Timestamp fechaFinDeCampeon) { this.fechaFinDeCampeon = fechaFinDeCampeon; }


    public Dojo getDojo() {
        return dojo;
    }

    public void setDojo(Dojo dojo) {
        this.dojo = dojo;
    }
}
