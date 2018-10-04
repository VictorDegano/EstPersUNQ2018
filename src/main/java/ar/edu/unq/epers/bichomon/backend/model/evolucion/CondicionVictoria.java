package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import javax.persistence.Entity;


@Entity
public class CondicionVictoria extends CondicionEvolucion
{
    private int victoriaCondicion;

    public CondicionVictoria(int victoriasACumplir)
    {   this.setVictoriaCondicion(victoriasACumplir);   }

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {   return unBicho.getVictorias() > this.getVictoriaCondicion();   }

    /*[--------]Constructor[--------]*/
    public CondicionVictoria() { }

    /*[--------]Getters & Setters[--------]*/
    public int getVictoriaCondicion() {  return victoriaCondicion;    }
    public void setVictoriaCondicion(int victoriaCondicion) { this.victoriaCondicion = victoriaCondicion;   }
}
