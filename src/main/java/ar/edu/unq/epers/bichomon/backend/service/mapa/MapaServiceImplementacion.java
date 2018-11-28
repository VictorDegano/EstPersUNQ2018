package ar.edu.unq.epers.bichomon.backend.service.mapa;

import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.excepcion.CaminoMuyCostoso;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionesInexistentesException;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeArribo;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import java.time.LocalDateTime;
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
    private EventoDAO eventoDAO;

    /**
     * El entrenador se movera a la ubicacion especificada
     *
     * @param entrenador - Nombre del entrenador que se movera
     * @param ubicacion  - Nombre de la ubicacion a moverse
     */
    @Override
    public void mover(String entrenador, String ubicacion)
    {
        Runner.runInSessionHibernateMongo(() -> {
                Entrenador entrenadorAMoverse   = this.getEntrenadorDAO().recuperar(entrenador);
                Ubicacion ubicacionAMoverse     = this.getUbicacionDAO().recuperar(ubicacion);
                Ubicacion ubicacionVieja        = entrenadorAMoverse.getUbicacion();

                Camino caminoATransitar         = this.getUbicacionDAONEO4J().caminoA(entrenadorAMoverse.getUbicacion().getNombre(),ubicacion);

                if (entrenadorAMoverse.puedeCostearViaje(caminoATransitar.getCosto()))
                {
                    entrenadorAMoverse.moverse(ubicacionAMoverse);
                    entrenadorAMoverse.sacarDeBilletera(caminoATransitar.getCosto());
                    eventoDAO.guardar(new EventoDeArribo(entrenador, ubicacionVieja.getNombre(), ubicacion, LocalDateTime.now()));

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
    public MapaServiceImplementacion(EntrenadorDAO unEntrenadorDAO, UbicacionDAO unUbicacionDAO, UbicacionDAONEO4J ubicacionDAONEO4J, EventoDAO unEventoDao)
    {
        this.setEntrenadorDAO(unEntrenadorDAO);
        this.setUbicacionDAO(unUbicacionDAO);
        this.setUbicacionDAONEO4J(ubicacionDAONEO4J);
        this.setEventoDAO(unEventoDao);
    }

/*[--------]Getters & Setters[--------]*/
    private EntrenadorDAO getEntrenadorDAO() {   return entrenadorDAO;   }
    private void setEntrenadorDAO(EntrenadorDAO entrenadorDAO) { this.entrenadorDAO = entrenadorDAO; }

    private UbicacionDAO getUbicacionDAO() { return ubicacionDAO;    }
    private void setUbicacionDAO(UbicacionDAO ubicacionDAO) {    this.ubicacionDAO = ubicacionDAO;   }

    private UbicacionDAONEO4J getUbicacionDAONEO4J(){return ubicacionDAONEO4J;}
    private void setUbicacionDAONEO4J(UbicacionDAONEO4J ubicacionDAONEO4J){this.ubicacionDAONEO4J=ubicacionDAONEO4J;}

    public EventoDAO getEventoDAO() {   return eventoDAO;   }
    public void setEventoDAO(EventoDAO eventoDAO) { this.eventoDAO = eventoDAO; }

/*[--------]Neo4J[--------]*/

    @Override
    public void moverMasCorto(String entrenador, String ubicacion) {
        Runner.runInSessionHibernateMongo(() -> {
            Entrenador    entrenadorRecuperado  = this.entrenadorDAO.recuperar(entrenador);
            Ubicacion     ubicacionActual       = entrenadorRecuperado.getUbicacion();
            Ubicacion     ubicacionDestino      = this.ubicacionDAO.recuperar(ubicacion);

            if (ubicacionDestino == null)
            {   throw new UbicacionIncorrectaException(ubicacion);  }

            List<Camino>  caminos               = this.ubicacionDAONEO4J.caminoMasCortoA(ubicacionActual, ubicacionDestino);
            int costo                           = caminos.stream().mapToInt(camino -> camino.getCosto()).sum();

            if (entrenadorRecuperado.puedeCostearViaje(costo))
            {
                entrenadorRecuperado.moverse(ubicacionDestino);
                entrenadorRecuperado.sacarDeBilletera(costo);

                this.crearEventosDeArriboPara(entrenador, caminos);

                this.getEntrenadorDAO().actualizar(entrenadorRecuperado);
                this.getUbicacionDAO().actualizar(ubicacionActual);
                this.getUbicacionDAO().actualizar(ubicacionDestino);
            }
            else
            {   throw new CaminoMuyCostoso(ubicacion);   }

            return null;
        });
    }

    private void crearEventosDeArriboPara(String entrenador, List<Camino> caminos)
    {
        List<Evento> eventosAAgregar    = new ArrayList<>();

        for (Camino camino : caminos)
        {   eventosAAgregar.add(new EventoDeArribo( entrenador,
                                                    camino.getDesdeUbicacion(),
                                                    camino.getHastaUbicacion(),
                                                    LocalDateTime.now()));  }

        eventoDAO.guardarTodos(eventosAAgregar);
    }

    @Override
    public List<Ubicacion> conectados(String ubicacion, String tipoCamino) {
        return Runner.runInSession(() -> {
            Ubicacion unaUbicacion  = this.ubicacionDAO.recuperar(ubicacion);

            if (unaUbicacion != null)
            {
                List<String> nombresDeUbicaciones = this.ubicacionDAONEO4J.conectados(unaUbicacion, TipoCamino.valueOf(tipoCamino));
                return  this.ubicacionDAO.recuperarUbicaciones(nombresDeUbicaciones);
            }
            else
            {   throw new UbicacionIncorrectaException(ubicacion);   }

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
            Ubicacion unaUbicacion  = this.ubicacionDAO.recuperar(ubicacion1);
            Ubicacion otraUbicacion = this.ubicacionDAO.recuperar(ubicacion2);

            if (unaUbicacion != null && otraUbicacion != null)
            {   this.ubicacionDAONEO4J.conectar(unaUbicacion, otraUbicacion, TipoCamino.valueOf(tipoCamino));   }
            else
            {   throw new UbicacionesInexistentesException(ubicacion1 + ", " + ubicacion2);   }
            return null;
        });
    }
}
