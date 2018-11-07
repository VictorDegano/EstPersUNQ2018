package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface UbicacionDAO
{
    void guardar(Ubicacion ubicacion);

    Ubicacion recuperar(String nombre);

    void actualizar(Ubicacion ubicacion);

    Bicho recuperarCampeonHistoricoDe(String dojo);

    List<Ubicacion> recuperarUbicaciones(List<String> nombresDeUbicaciones);
}
