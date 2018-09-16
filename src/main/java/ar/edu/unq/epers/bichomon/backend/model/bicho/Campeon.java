package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import java.sql.Timestamp;

public class Campeon
{
    private int id;
    // TODO: 16/09/2018 OneToOne? Un Campeon Es Solo Un Bicho.
    private Bicho bichoCampeon;
    // TODO: 16/09/2018 OneToOne? Si es desplazado no romperia?
    private Dojo dojo;
    private Timestamp fechaInicioDeCampeon;
    private Timestamp fechaFinDeCampeon;

/*[--------]Constructor[--------]*/
    public Campeon() {  }

/*[--------]Getters & Setters[--------]*/
    public Bicho getBichoCampeon() {    return bichoCampeon;    }
    public void setBichoCampeon(Bicho bichoCampeon) {   this.bichoCampeon = bichoCampeon;   }

    public Timestamp getFechaInicioDeCampeon() {    return fechaInicioDeCampeon;    }
    public void setFechaInicioDeCampeon(Timestamp fechaInicioDeCampeon) {   this.fechaInicioDeCampeon = fechaInicioDeCampeon;   }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }

    public Dojo getDojo() {    return dojo;   }
    public void setDojo(Dojo dojo) {  this.dojo = dojo; }
}
