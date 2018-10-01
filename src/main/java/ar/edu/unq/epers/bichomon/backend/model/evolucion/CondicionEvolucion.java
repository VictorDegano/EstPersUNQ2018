package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

public interface CondicionEvolucion
{
    boolean cumpleCondicion(Bicho unBicho);
}
