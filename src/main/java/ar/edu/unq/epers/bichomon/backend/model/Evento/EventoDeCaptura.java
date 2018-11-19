package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public class EventoDeCaptura extends Evento {
    Bicho bichoCapturado;

    public Bicho getBichoCapturado() {

        return bichoCapturado;
    }

    public void setBichoCapturado(Bicho bichoCapturado) {
        this.bichoCapturado = bichoCapturado;
    }


}
