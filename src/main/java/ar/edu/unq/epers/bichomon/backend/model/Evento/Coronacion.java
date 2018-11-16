package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public class Coronacion extends Evento {
    public Entrenador getEntrenadorDestronado() {
        return entrenadorDestronado;
    }

    public void setEntrenadorDestronado(Entrenador entrenadorDestronado) {
        this.entrenadorDestronado = entrenadorDestronado;
    }

    Entrenador entrenadorDestronado;



}
