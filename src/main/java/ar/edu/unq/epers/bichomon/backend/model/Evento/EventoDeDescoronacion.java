package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class EventoDeDescoronacion extends Evento {
    public Entrenador getEntrenadorCoronado() {
        return entrenadorCoronado;
    }

    public void setEntrenadorCoronado(Entrenador entrenadorCoronado) {
        this.entrenadorCoronado = entrenadorCoronado;
    }

    Entrenador entrenadorCoronado;
}
