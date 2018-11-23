package ar.edu.unq.epers.bichomon.backend.model.Evento;

import java.time.LocalDateTime;

public class EventoDeDescoronacion extends Evento {
    private String entrenadorCoronado;

    public EventoDeDescoronacion(String entrenadorDescoronado, String ubicacion, String entrenadorCoronado, LocalDateTime fechaDeDescoronacion)
    {
        this.setEntrenador(entrenadorDescoronado);
        this.setUbicacion(ubicacion);
        this.setEntrenadorCoronado(entrenadorCoronado);
        this.setFechaDeEvento(fechaDeDescoronacion);
    }

    public EventoDeDescoronacion() {    }

    public String getEntrenadorCoronado() {
        return entrenadorCoronado;
    }

    public void setEntrenadorCoronado(String entrenadorCoronado) {
        this.entrenadorCoronado = entrenadorCoronado;
    }


}
