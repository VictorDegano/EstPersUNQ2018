package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class CondicionVictoria implements CondicionEvolucion
{
    private int victoriaCondicion;

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {   return unBicho.getVictorias() > this.getVictoriaCondicion();   }

    /*[--------]Constructor[--------]*/
    public CondicionVictoria() { }

    /*[--------]Getters & Setters[--------]*/
    public int getVictoriaCondicion() {  return victoriaCondicion;    }
    public void setVictoriaCondicion(int victoriaCondicion) { this.victoriaCondicion = victoriaCondicion;   }
}
