package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import java.util.List;

public interface EntrenadorDAO
{
    void guardar(Entrenador entrenador);

    Entrenador recuperar(String nombre);

    void actualizar(Entrenador entrenador);

    List<Entrenador> campeones();

    List<Entrenador> lideres();
}
