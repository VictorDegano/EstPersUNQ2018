package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.List;

public interface MapaService
{
    /**
     * El entrenador se movera a la ubicacion especificada
     * @param entrenador - Nombre del entrenador que se movera
     * @param ubicacion - Nombre de la ubicacion a moverse
     */
    void mover(String entrenador, String ubicacion);

    /**
     * Retorna la cantidad de entrenadores que se encuentran en la ubicacion especificada
     * @param ubicacion - Nombre de la ubicacion
     * @return Int - Cantidad de entrenadores en la ubicacion especificada
     */
    int cantidadEntrenadores(String ubicacion);

    /**
     * Retrona al actual bicho campeon del dojo
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El actual campeon del dojo
     */
    Bicho campeon(String dojo);

    /**
     * Retorna al bicho que ha sido campeon por mas tiempo en el dojo.
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El bicho que ha sido campeon por mas tiempo en el dojo
     */
    Bicho campeonHistorico(String dojo);

    void moverMasCorto(String entrenador, String ubicacion);

    List<Ubicacion> conectados(String ubicacion, String tipoCamino);

    void crearUbicacion(Ubicacion ubicacion);

    void conectar(String ubicacion1, String ubicacion2, String tipoCamino);
}
