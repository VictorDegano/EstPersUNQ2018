package ar.edu.unq.epers.bichomon.backend.model.Evento;

import java.time.LocalDateTime;

public class EventoDeDescoronacion extends Evento {

    public EventoDeDescoronacion(String entrenadorDescoronado, String ubicacion, LocalDateTime fechaDeDescoronacion)
    {
        this.setEntrenador(entrenadorDescoronado);
        this.setUbicacion(ubicacion);
        this.setFechaDeEvento(fechaDeDescoronacion);
    }

    public EventoDeDescoronacion() {    }

}
