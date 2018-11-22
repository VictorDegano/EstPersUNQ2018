package ar.edu.unq.epers.bichomon.backend.model.Evento;


import java.time.LocalDateTime;

public class EventoDeCoronacion extends Evento {
    private String entrenadorDestronado;

    public EventoDeCoronacion(String entrenadorCoronado, String ubicacion, String entrenadorDescoronado, LocalDateTime fechaDeDuelo)
    {
        this.setEntrenador(entrenadorCoronado);
        this.setUbicacion(ubicacion);
        this.setEntrenadorDestronado(entrenadorDescoronado);
        this.setFechaDeEvento(fechaDeDuelo);
    }

    public EventoDeCoronacion() {   }

    public String getEntrenadorDestronado() {
        return entrenadorDestronado;
    }

    public void setEntrenadorDestronado(String entrenadorDestronado) {
        this.entrenadorDestronado = entrenadorDestronado;
    }





}
