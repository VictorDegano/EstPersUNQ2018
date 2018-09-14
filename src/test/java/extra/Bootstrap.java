package extra;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

public class Bootstrap
{

    public void crearDatos()
    {
        Ubicacion ubicacion1  = new Ubicacion();
        ubicacion1.setNombre("El Origen");

        Ubicacion ubicacion2  = new Ubicacion();
        ubicacion2.setNombre("Desert");

        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(0);
        entrenador1.setNivel(1);

        Entrenador entrenador2  = new Entrenador();
        entrenador2.setNombre("El Loquillo");
        entrenador2.setExperiencia(990);
        entrenador2.setNivel(3);

        entrenador1.setUbicacion(ubicacion1);
        ubicacion1.agregarEntrenador(entrenador1);
        entrenador2.setUbicacion(ubicacion2);
        ubicacion2.agregarEntrenador(entrenador2);

        Session session = Runner.getCurrentSession();
        session.save(ubicacion1);
        session.save(ubicacion2);
        session.save(entrenador1);
        session.save(entrenador2);
    }
}
