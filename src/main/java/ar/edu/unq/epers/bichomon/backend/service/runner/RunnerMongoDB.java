package ar.edu.unq.epers.bichomon.backend.service.runner;

import ar.edu.unq.epers.bichomon.backend.service.MongoConnection;
import org.hibernate.Session;
import org.jongo.Jongo;

import java.util.function.Supplier;

public class RunnerMongoDB {

   // private static final ThreadLocal<Session> CONTEXTO = new ThreadLocal<>();

    public static <T> T runInSession(Supplier<T> bloque) {
        MongoConnection coneccion = MongoConnection.getInstance();
        Jongo jongo = coneccion.getJongo();
        try {
            coneccion = MongoConnection.getInstance();
            jongo = coneccion.getJongo();
            jongo.runCommand("{Session.startTransaction():1}");

            T resultado = bloque.get();

            jongo.runCommand("{Session.commitTransaction():1");
            return resultado;
        }

        catch(RuntimeException e){
            jongo.runCommand("{Session.abortTransaction():1");
            throw e;
        }

    }
}
