package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.excepcion.CaminoMuyCostoso;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa los servicios necesarios para la utilizacion de un "mapa".
 */
public class MapaServiceImplementacion implements MapaService
{
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private UbicacionDAONEO4J ubicacionDAONEO4J;

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
                Ubicacion ubicacionAMoverse = this.getUbicacionDAO().recuperar(ubicacion);
                Ubicacion ubicacionVieja = entrenadorAMoverse.getUbicacion();

                Camino caminoATransitar         = this.getUbicacionDAONEO4J().caminoA(entrenadorAMoverse.getUbicacion().getNombre(),ubicacion);

                if (entrenadorAMoverse.puedeCostearViaje(caminoATransitar.getCosto()))
                {
                    entrenadorAMoverse.moverse(ubicacionAMoverse);
                    entrenadorAMoverse.sacarDeBilletera(caminoATransitar.getCosto());

                    this.getEntrenadorDAO().actualizar(entrenadorAMoverse);
                    this.getUbicacionDAO().actualizar(ubicacionVieja);
                    this.getUbicacionDAO().actualizar(ubicacionAMoverse);
                }
                else
                {   throw new CaminoMuyCostoso(ubicacion);   }

                return null;
              });
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
                });
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
            return  this.getUbicacionDAO().recuperarCampeonHistoricoDe(dojo);
        });
    }

    /*[--------]Constructors[--------]*/
    public MapaServiceImplementacion(EntrenadorDAO unEntrenadorDAO, UbicacionDAO unUbicacionDAO, UbicacionDAONEO4J ubicacionDAONEO4J)
    {
        this.setEntrenadorDAO(unEntrenadorDAO);
        this.setUbicacionDAO(unUbicacionDAO);
        this.setUbicacionDAONEO4J(ubicacionDAONEO4J);
    }

/*[--------]Getters & Setters[--------]*/
    private EntrenadorDAO getEntrenadorDAO() {   return entrenadorDAO;   }
    private void setEntrenadorDAO(EntrenadorDAO entrenadorDAO) { this.entrenadorDAO = entrenadorDAO; }

    private UbicacionDAO getUbicacionDAO() { return ubicacionDAO;    }
    private void setUbicacionDAO(UbicacionDAO ubicacionDAO) {    this.ubicacionDAO = ubicacionDAO;   }

    private UbicacionDAONEO4J getUbicacionDAONEO4J(){return ubicacionDAONEO4J;}
    private void setUbicacionDAONEO4J(UbicacionDAONEO4J ubicacionDAONEO4J){this.ubicacionDAONEO4J=ubicacionDAONEO4J;}

/*[--------]Neo4J[--------]*/

    @Override
    public void moverMasCorto(String entrenador, String ubicacion) {

    }

    @Override
    public List<Ubicacion> conectados(String ubicacion, String tipoCamino) {
        return Runner.runInSession(() -> {
            List<String> nombresDeUbicaciones = this.ubicacionDAONEO4J.conectados(ubicacion, tipoCamino);
            return  this.ubicacionDAO.recuperarUbicaciones(nombresDeUbicaciones);
        });
    }


    @Override
    public void crearUbicacion(Ubicacion ubicacion) {
        Runner.runInSession(() -> {
            this.ubicacionDAO.guardar(ubicacion);
            this.ubicacionDAONEO4J.create(ubicacion);
            return null;
        });

    }

    @Override
    public void conectar(String ubicacion1, String ubicacion2, String tipoCamino) {
        Runner.runInSession(() -> {
            this.ubicacionDAONEO4J.conectar(ubicacion1, ubicacion2, TipoCamino.valueOf(tipoCamino));
            return null;
        });
    }

}
