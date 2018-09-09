package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

public interface UbicacionDAO
{
    void guardar(Ubicacion ubicacion);

    Ubicacion recuperar(String nombre);

    void actualizar(Ubicacion ubicacion);
}
