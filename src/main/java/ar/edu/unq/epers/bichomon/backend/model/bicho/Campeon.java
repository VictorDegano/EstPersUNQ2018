package ar.edu.unq.epers.bichomon.backend.model.bicho;

import java.sql.Timestamp;

public class Campeon
{
    private Bicho bichoCampeon;
    private Timestamp fechaInicioDeCampeon;

/*[--------]Constructor[--------]*/
    public Campeon() {  }

/*[--------]Getters & Setters[--------]*/
    public Bicho getBichoCampeon() {    return bichoCampeon;    }
    public void setBichoCampeon(Bicho bichoCampeon) {   this.bichoCampeon = bichoCampeon;   }

    public Timestamp getFechaInicioDeCampeon() {    return fechaInicioDeCampeon;    }
    public void setFechaInicioDeCampeon(Timestamp fechaInicioDeCampeon) {   this.fechaInicioDeCampeon = fechaInicioDeCampeon;   }
}
