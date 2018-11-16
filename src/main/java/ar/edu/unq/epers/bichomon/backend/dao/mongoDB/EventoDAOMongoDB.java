package ar.edu.unq.epers.bichomon.backend.dao.mongoDB;

import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import org.jongo.MongoCollection;

public class EventoDAOMongoDB implements EventoDAO {

    protected MongoCollection mongoCollection;
    
    @Override
    public void guardar(){

    }

    @Override
    public Evento recuperar(String id){
        return null;
    }

    @Override
    public void actualizar(Evento evento) {

    }

}
