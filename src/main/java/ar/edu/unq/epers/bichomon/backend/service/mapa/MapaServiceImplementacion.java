package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

/**
 * Clase que implementa los servicios necesarios para la utilizacion de un "mapa".
 */
public class MapaServiceImplementacion implements MapaService
{
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;

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
                                    Ubicacion ubicacionAMoverse     = this.getUbicacionDAO().recuperar(ubicacion);

                                    if( (entrenadorAMoverse != null) && (ubicacionAMoverse != null))
                                    {
                                        Ubicacion ubicacionVieja        = entrenadorAMoverse.getUbicacion();

                                        entrenadorAMoverse.moverse(ubicacionAMoverse);

                                        this.getEntrenadorDAO().actualizar(entrenadorAMoverse);
                                        this.getUbicacionDAO().actualizar(ubicacionVieja);
                                        this.getUbicacionDAO().actualizar(ubicacionAMoverse);
                                    }
                                    else
                                    {   throw new RuntimeException("Nombre de entrenador: " + entrenador + " o nombre de ubicacion: "+ ubicacion +" incorrectos");  }
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
    public int cantidadEntrenadores(String ubicacion)
    {
        return Runner.runInSession(() -> {
                    Ubicacion unaUbicacion = this.getUbicacionDAO().recuperar(ubicacion);

                    if( unaUbicacion != null)
                    {   return unaUbicacion.cantidadDeEntrenadores();   }
                    else
                    {   throw new UbicacionIncorrectaException(ubicacion);  }
                }
        );
    }

    /**
     * Retrona al actual bicho campeon del dojo
     *
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El actual campeon del dojo
     */
    @Override
    public Bicho campeon(String dojo) {
        return Runner.runInSession(() -> {
                    Ubicacion unDojo = this.getUbicacionDAO().recuperar(dojo);

                    if( unDojo != null)
                    {   return unDojo.campeonActual();   }
                    else
                    {   throw new UbicacionIncorrectaException(dojo);  }
                }
        );
    }

    /**
     * Retorna al bicho que ha sido campeon por mas tiempo en el dojo.
     *
     * @param dojo - Nombre del dojo
     * @return {@link Bicho} - El bicho que ha sido campeon por mas tiempo en el dojo
     */
    @Override
    public Bicho campeonHistorico(String dojo) {
        return Runner.runInSession(() -> {
                    Ubicacion unDojo = this.getUbicacionDAO().recuperar(dojo);

                    if( unDojo != null)
                    {   return unDojo.campeonActual();   }
                    else
                    {   throw new UbicacionIncorrectaException(dojo);  }
                }
        );
    }

/*[--------]Constructors[--------]*/
    public MapaServiceImplementacion(EntrenadorDAO unEntrenadorDAO, UbicacionDAO unUbicacionDAO)
    {
        this.setEntrenadorDAO(unEntrenadorDAO);
        this.setUbicacionDAO(unUbicacionDAO);
    }

/*[--------]Getters & Setters[--------]*/
    private EntrenadorDAO getEntrenadorDAO() {   return entrenadorDAO;   }
    private void setEntrenadorDAO(EntrenadorDAO entrenadorDAO) { this.entrenadorDAO = entrenadorDAO; }

    private UbicacionDAO getUbicacionDAO() { return ubicacionDAO;    }
    private void setUbicacionDAO(UbicacionDAO ubicacionDAO) {    this.ubicacionDAO = ubicacionDAO;   }
}
