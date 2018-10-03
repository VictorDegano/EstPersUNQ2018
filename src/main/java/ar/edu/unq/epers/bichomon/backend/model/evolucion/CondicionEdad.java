package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class CondicionEdad extends CondicionEvolucion
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
