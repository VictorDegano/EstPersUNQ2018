package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

/** TODO: Hacer que sea implementado cuando se implemente la clase DAO*/
public interface EntrenadorDAO
{
    void guardar(Entrenador entrenador);

    Entrenador recuperar(String nombre);
}
