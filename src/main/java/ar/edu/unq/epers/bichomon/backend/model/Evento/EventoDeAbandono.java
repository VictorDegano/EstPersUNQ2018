package ar.edu.unq.epers.bichomon.backend.model.Evento;

import java.time.LocalDateTime;

public class EventoDeAbandono extends Evento {

    private String bichoAbandonado;

    /*[--------]Constructors[--------]*/
    public EventoDeAbandono(String entrenador, String unaUbicacion, String especieDeBicho, LocalDateTime unLocalDate)
    {
        this.setEntrenador(entrenador);
        this.setUbicacion(unaUbicacion);
        this.setBichoAbandonado(especieDeBicho);
        this.setFechaDeEvento(unLocalDate);
    }

    public EventoDeAbandono() { }

    /*[--------]Getters & Setters[--------]*/
    public String getBichoAbandonado() {
        return bichoAbandonado;
    }
    public void setBichoAbandonado(String bichoAbandonado) {
        this.bichoAbandonado = bichoAbandonado;
    }
}
