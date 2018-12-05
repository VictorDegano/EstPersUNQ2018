package ar.edu.unq.epers.bichomon.backend.model.Evento;


import java.time.LocalDateTime;

public class EventoDeCoronacion extends Evento
{

    public EventoDeCoronacion(String entrenadorCoronado, String ubicacion, LocalDateTime fechaDeDuelo)
    {
        this.setEntrenador(entrenadorCoronado);
        this.setUbicacion(ubicacion);
        this.setFechaDeEvento(fechaDeDuelo);
    }

    public EventoDeCoronacion() {   }

}
