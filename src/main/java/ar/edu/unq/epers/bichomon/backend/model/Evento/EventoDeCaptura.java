package ar.edu.unq.epers.bichomon.backend.model.Evento;

import java.time.LocalDateTime;

public class EventoDeCaptura extends Evento {

    private String especieBichoCapturado;

    public EventoDeCaptura() {}

    public EventoDeCaptura(String entrenador, String ubicacion, String especie, LocalDateTime now)
    {
        this.setEntrenador(entrenador);
        this.setUbicacion(ubicacion);
        this.setEspecieBichoCapturado(especie);
        this.setFechaDeEvento(now);
    }

    public String getEspecieBichoCapturado() {  return especieBichoCapturado;   }

    public void setEspecieBichoCapturado(String especieBichoCapturado) {    this.especieBichoCapturado = especieBichoCapturado; }
}
