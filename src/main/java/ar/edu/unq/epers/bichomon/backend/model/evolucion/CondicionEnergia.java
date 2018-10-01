package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class CondicionEnergia implements CondicionEvolucion
{
    private int energiaCondicion;

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {   return unBicho.getEnergia() > this.getEnergiaCondicion();   }

/*[--------]Constructor[--------]*/
    public CondicionEnergia() { }

/*[--------]Getters & Setters[--------]*/
    public int getEnergiaCondicion() {  return energiaCondicion;    }
    public void setEnergiaCondicion(int energiaCondicion) { this.energiaCondicion = energiaCondicion;   }
}
