package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class CondicionNivel implements CondicionEvolucion
{
    private int nivelCondicion;

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {   return unBicho.getDuenio().getNivel().getNroDeNivel() > this.getNivelCondicion();   }

    /*[--------]Constructor[--------]*/
    public CondicionNivel() { }

    /*[--------]Getters & Setters[--------]*/
    public int getNivelCondicion() {  return nivelCondicion;    }
    public void setNivelCondicion(int nivelCondicion) { this.nivelCondicion = nivelCondicion;   }
}
