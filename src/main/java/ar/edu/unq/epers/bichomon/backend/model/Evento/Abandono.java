package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public class Abandono extends Evento  {

    Bicho bichoAbandonado;
    Ubicacion guarderia;
    public Bicho getBichoAbandonado() {
        return bichoAbandonado;
    }

    public void setBichoAbandonado(Bicho bichoAbandonado) {
        this.bichoAbandonado = bichoAbandonado;
    }

    public Ubicacion getGuarderia() {
        return guarderia;
    }

    public void setGuarderia(Ubicacion guarderia) {
        this.guarderia = guarderia;
    }

}
