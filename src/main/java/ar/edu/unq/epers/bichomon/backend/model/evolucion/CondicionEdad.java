package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import java.time.Duration;
import java.time.LocalDateTime;

public class CondicionEdad implements CondicionEvolucion
{
    private int edadCondicion;

    @Override
    public boolean cumpleCondicion(Bicho unBicho)
    {
        Duration tiempoDeVida    = Duration.between(unBicho.getFechaDeCaptura().toLocalDateTime(), LocalDateTime.now());
        return tiempoDeVida.toDays() > this.getEdadCondicion();   }

    /*[--------]Constructor[--------]*/
    public CondicionEdad() { }

    /*[--------]Getters & Setters[--------]*/
    public int getEdadCondicion() {  return edadCondicion;    }
    public void setEdadCondicion(int edadEnDias) { this.edadCondicion = edadEnDias;   }
}
