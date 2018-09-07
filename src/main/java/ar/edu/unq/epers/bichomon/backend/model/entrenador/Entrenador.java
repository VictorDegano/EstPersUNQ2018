package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Entrenador
{
    private String nombre       = "";
    private int experiencia     = 0;
    private int nivel           = 1;
    private Ubicacion ubicacion = null;

    public void moverse(Ubicacion unaNuevaUbicacion)
    {
        this.ubicacion.quitarEntrenador(this);
        this.ubicacion = unaNuevaUbicacion;
        unaNuevaUbicacion.agregarEntrenador(this);
    }

}
