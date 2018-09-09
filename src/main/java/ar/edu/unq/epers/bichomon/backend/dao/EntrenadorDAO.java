package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

public interface EntrenadorDAO
{
    void guardar(Entrenador entrenador);

    Entrenador recuperar(String nombre);
}
