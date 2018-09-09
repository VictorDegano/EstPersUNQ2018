package extra;

import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import javax.persistence.Query;

public class Limpiador
{

    private static Limpiador INSTANCE;

    public static Limpiador getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Limpiador();
        }
        return INSTANCE;
    }

    public static void destroy() {  INSTANCE = null;    }

    public void limpiarTabla()
    {
        Runner.runInSession(()-> {
                                    Session session = Runner.getCurrentSession();
                                    Query query1 = session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;");
                                    Query query2 = session.createNativeQuery("TRUNCATE TABLE ubicacion;");
                                    Query query3 = session.createNativeQuery("TRUNCATE TABLE entrenador;");
                                    Query query4 = session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;");
                                    query1.executeUpdate();
                                    query2.executeUpdate();
                                    query3.executeUpdate();
                                    query4.executeUpdate();
                                    return null;
                                 });
    }

}
