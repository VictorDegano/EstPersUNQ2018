package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que implementa los servicios necesarios para la utilizacion de un "mapa".
 */
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class MapaServiceImplementacion implements MapaService
{
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;

    public MapaServiceImplementacion(EntrenadorDAO unEntrenadorDAO, UbicacionDAO unUbicacionDAO)
    {
        entrenadorDAO   = unEntrenadorDAO;
        ubicacionDAO    = unUbicacionDAO;
    }

    /**
     * El entrenador se movera a la ubicacion especificada
     *
     * @param entrenador - Nombre del entrenador que se movera
     * @param ubicacion  - Nombre de la ubicacion a moverse
     */
    @Override
    public void mover(String entrenador, String ubicacion)
    {
        Runner.runInSession(() -> {
                                    Entrenador entrenadorAMoverse   = this.getEntrenadorDAO().recuperar(entrenador);
                                    Ubicacion ubicacionVieja        = entrenadorAMoverse.getUbicacion();
                                    Ubicacion ubicacionAMoverse     = this.getUbicacionDAO().recuperar(ubicacion);

                                    entrenadorAMoverse.moverse(ubicacionAMoverse);

                                    this.getEntrenadorDAO().guardar(entrenadorAMoverse);
                                    this.getUbicacionDAO().guardar(ubicacionVieja);
                                    this.getUbicacionDAO().guardar(ubicacionAMoverse);
                                    return null;
                                  }
                           );
    }

    /**
     * Retorna la cantidad de entrenadores que se encuentran en la ubicacion especificada
     *
     * @param ubicacion - Nombre de la ubicacion
     * @return Int - Cantidad de entrenadores en la ubicacion especificada
     */
    @Override
    public int cantidadEntrenadores(String ubicacion) {
        return 0;
    }

    /**
     * Retrona al actual bicho campeon del dojo
     *
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El actual campeon del dojo
     */
    @Override
    public Bicho campeon(String dojo) {
        return null;
    }

    /**
     * Retorna al bicho que ha sido campeon por mas tiempo en el dojo.
     *
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El bicho que ha sido campeon por mas tiempo en el dojo
     */
    @Override
    public Bicho campeonHistorico(String dojo) {
        return null;
    }
}
