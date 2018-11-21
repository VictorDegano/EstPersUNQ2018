package ar.edu.unq.epers.bichomon.backend.dao.mongoDB;

import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.excepcion.EventoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.service.MongoConnection;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Evento resultado = this.mongoCollection.findOne(objectId).as(Evento.class);
        if (resultado != null)
        {   return resultado;   }
        else
        {   throw new EventoRecuperarException(id); }

    }

    public List<Evento> feedDeEntrenador(String entrenador)
    {
        String query        = "{ entrenador: # }";
        String querySort    = "{ fechaDeEvento: -1}";
        List<Evento> resultado  = new ArrayList<>();
        try
        {
            MongoCursor<Evento> all = this.mongoCollection.find(query,entrenador).sort(querySort).as(Evento.class);

            all.forEach(x -> resultado.add(x));

            all.close();

                return resultado;
        }
        catch (IOException e)
        {   throw new RuntimeException(e);  }
    }

    @Override
    public void actualizar(Evento evento)
    {

    }

    public void deleteAll()     {   this.mongoCollection.drop();    }
}
