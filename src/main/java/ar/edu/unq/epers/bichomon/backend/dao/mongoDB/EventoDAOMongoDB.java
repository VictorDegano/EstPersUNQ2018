package ar.edu.unq.epers.bichomon.backend.dao.mongoDB;

import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.service.MongoConnection;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class EventoDAOMongoDB implements EventoDAO
{
    protected MongoCollection mongoCollection;

    private MongoCollection getCollectionFor()
    {
        Jongo jongo = MongoConnection.getInstance().getJongo();
        return jongo.getCollection(Evento.class.getSimpleName());
    }

    public EventoDAOMongoDB()
    {   this.mongoCollection = this.getCollectionFor(); }

    @Override
    public void guardar(Evento evento) {   this.mongoCollection.insert(evento);    }

    @Override
    public Evento recuperar(String id)
    {
        ObjectId objectId = new ObjectId(id);
        return this.mongoCollection.findOne(objectId).as(Evento.class);
    }

    @Override
    public void actualizar(Evento evento)
    {

    }

    public void deleteAll()     {   this.mongoCollection.drop();    }
}
