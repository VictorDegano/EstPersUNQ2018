package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class EventoDeAbandono extends Evento {

    Bicho bichoAbandonado;

    public Bicho getBichoAbandonado() {
        return bichoAbandonado;
    }

    public void setBichoAbandonado(Bicho bichoAbandonado) {
        this.bichoAbandonado = bichoAbandonado;
    }
}
