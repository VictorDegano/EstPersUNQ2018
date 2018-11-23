package ar.edu.unq.epers.bichomon.backend.service.feed;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedServiceImplementation implements FeedService
{
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private EventoDAO eventoDAO;

    public FeedServiceImplementation(EntrenadorDAO entrenadorDAO, UbicacionDAONEO4J ubicacionDAONEO4J, EventoDAO eventoDAOMongoDB)
    {
        this.entrenadorDAO      = entrenadorDAO;
        this.ubicacionDAONEO4J  = ubicacionDAONEO4J;
        this.eventoDAO          = eventoDAOMongoDB;
    }

    @Override
    public List<Evento> feedEntrenador(String entrenador)
    {
        return Runner.runInSession(() -> {
            Entrenador entrenadorRecuperado = this.entrenadorDAO.recuperar(entrenador);
            return this.eventoDAO.feedDeEntrenador(entrenadorRecuperado);
        });
    }

    @Override
    public List<Evento> feedUbicacion(String entrenador)
    {
        return Runner.runInSession(() -> {
                    Entrenador entrenadorRecuperado = this.entrenadorDAO.recuperar(entrenador);

                    ArrayList<String> ubicacionesABuscarEventos  = new ArrayList<>();
                    ubicacionesABuscarEventos.add(entrenadorRecuperado.getUbicacion().getNombre());

                    ubicacionesABuscarEventos.addAll(this.ubicacionDAONEO4J.conectados(TipoCamino.TERRESTRE.name(), entrenadorRecuperado.getUbicacion().getNombre()));
                    ubicacionesABuscarEventos.addAll(this.ubicacionDAONEO4J.conectados(TipoCamino.MARITIMO.name(), entrenadorRecuperado.getUbicacion().getNombre()));
                    ubicacionesABuscarEventos.addAll(this.ubicacionDAONEO4J.conectados(TipoCamino.AEREO.name(), entrenadorRecuperado.getUbicacion().getNombre()));

                    return this.eventoDAO.feedDeUbicaciones(ubicacionesABuscarEventos);
                });
    }

    public EntrenadorDAO getEntrenadorDAO() {   return entrenadorDAO;   }
    public void setEntrenadorDAO(EntrenadorDAO entrenadorDAO) { this.entrenadorDAO = entrenadorDAO; }

    public UbicacionDAONEO4J getUbicacionDAONEO4J() {   return ubicacionDAONEO4J;   }
    public void setUbicacionDAONEO4J(UbicacionDAONEO4J ubicacionDAONEO4J) { this.ubicacionDAONEO4J = ubicacionDAONEO4J; }

    public EventoDAO getEventoDAO() {   return eventoDAO;   }
    public void setEventoDAO(EventoDAO eventoDAO) { this.eventoDAO = eventoDAO; }
}
