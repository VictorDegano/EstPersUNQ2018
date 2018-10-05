package ar.edu.unq.epers.bichomon.backend.dao;

public interface ExperienciaDAO
{
    void guardar(String nombre, int valor);

    int recuperar(String nombre);

    void actualizar(String nombre, int valor);
}
