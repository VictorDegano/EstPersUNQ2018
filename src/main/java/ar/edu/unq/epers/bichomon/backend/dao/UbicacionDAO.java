package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

/** TODO: Hacer que sea implementado cuando se implemente la clase DAO*/
public interface UbicacionDAO
{
    void guardar(Ubicacion ubicacion);

    Ubicacion recuperar(String nombre);
}
