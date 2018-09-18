package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class Dojo extends Ubicacion
{
    @Transient
    private Campeon campeonActual;

    public Bicho campeonActual()
    {   return campeonActual.getBichoCampeon(); }

/*[--------]Constructors[--------]*/
    public Dojo() {}

/*[--------]Getters & Setters[--------]*/
    public Campeon getCampeonActual() { return campeonActual;   }
    public void setCampeonActual(Campeon campeonActual) {   this.campeonActual = campeonActual; }
}