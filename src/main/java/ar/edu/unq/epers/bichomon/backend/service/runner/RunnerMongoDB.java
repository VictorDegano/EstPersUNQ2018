package ar.edu.unq.epers.bichomon.backend.service.runner;

import ar.edu.unq.epers.bichomon.backend.service.MongoConnection;
import com.mongodb.MongoException;
import com.mongodb.session.ClientSession;
import org.jongo.Jongo;

import java.util.function.Supplier;

public class RunnerMongoDB
{
    public static <T> T runInSession(Supplier<T> bloque)
    {

        MongoConnection coneccion = MongoConnection.getInstance();
        Jongo jongo = coneccion.getJongo();
        jongo.runCommand("{beginTransaction:1}");
        try
        {
            T resultado = bloque.get();
            jongo.runCommand("{commitTransaction:1}");
            return resultado;
        }
        catch(MongoException e)
        {
            jongo.runCommand("{abortTransaction:1}");
            throw e;
        }
    }
}
