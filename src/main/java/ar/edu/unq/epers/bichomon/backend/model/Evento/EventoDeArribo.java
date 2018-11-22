package ar.edu.unq.epers.bichomon.backend.model.Evento;


import java.time.LocalDateTime;

public class EventoDeArribo extends Evento
{
    private String ubicacionPartida;

    /*[--------]Constructors[--------]*/
    public EventoDeArribo(String entrenador, String ubicacionVieja, String ubicacionNueva, LocalDateTime unLocalDateTime)
    {
        this.setEntrenador(entrenador);
        this.setUbicacionPartida(ubicacionVieja);
        this.setUbicacion(ubicacionNueva);
        this.setFechaDeEvento(unLocalDateTime);
    }

    public EventoDeArribo() {   }

    /*[--------]Getters & Setters[--------]*/
    public String getUbicacionPartida() {
        return ubicacionPartida;
    }
    public void setUbicacionPartida(String ubicacionPartida) {
        this.ubicacionPartida = ubicacionPartida;
    }


}
