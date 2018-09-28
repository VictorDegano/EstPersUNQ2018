package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Dojo extends Ubicacion
{
    @OneToOne(cascade = CascadeType.ALL)
    private Campeon campeonActual;

    public Bicho campeonActual()
    {
        if (this.getCampeonActual() == null)
        {   return null;    }
        return this.campeonActual.getBichoCampeon();
    }

/*[--------]Constructors[--------]*/
    public Dojo() {}

/*[--------]Getters & Setters[--------]*/
    public Campeon getCampeonActual() { return campeonActual;   }
    public void setCampeonActual(Campeon campeonActual) {   this.campeonActual = campeonActual; }
}
