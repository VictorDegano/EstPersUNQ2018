package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class Arribo extends Evento {
    private Ubicacion ubicacionPartida;


    public Ubicacion getUbicacionPartida() {
        return ubicacionPartida;
    }

    public void setUbicacionPartida(Ubicacion ubicacionPartida) {
        this.ubicacionPartida = ubicacionPartida;
    }


}
