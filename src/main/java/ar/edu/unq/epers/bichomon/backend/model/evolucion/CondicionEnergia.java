package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import javax.persistence.Entity;

@Entity
public class CondicionEnergia extends CondicionEvolucion
{
    private int energiaCondicion;

    public CondicionEnergia(int energiaACumplir)
    {   this.setEnergiaCondicion(energiaACumplir);  }

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {   return unBicho.getEnergia() > this.getEnergiaCondicion();   }

/*[--------]Constructor[--------]*/
    public CondicionEnergia() { }

/*[--------]Getters & Setters[--------]*/
    public int getEnergiaCondicion() {  return energiaCondicion;    }
    public void setEnergiaCondicion(int energiaCondicion) { this.energiaCondicion = energiaCondicion;   }
}
