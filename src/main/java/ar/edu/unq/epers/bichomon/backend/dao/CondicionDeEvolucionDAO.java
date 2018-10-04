package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;

public interface CondicionDeEvolucionDAO
{
    void guardar(CondicionEvolucion condicion);

    CondicionEvolucion recuperar(int idCondicion);
}
