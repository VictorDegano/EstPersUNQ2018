package ar.edu.unq.epers.bichomon.backend.mock;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta es una implementacion mock de EntrenadorDAO para fines de construccion del modelo y services
 * sin involucrar a la BD
 */
public class EntrenadorDAOMock implements EntrenadorDAO
{
    private static Map<String, Entrenador> DATA = new HashMap<>();

    static  {
                UbicacionDAO ubicacionDaoMock   = new UbicacionDAOMock();
                Ubicacion elOrigen              = ubicacionDaoMock.recuperar("El Origen");

                Entrenador joseRed = new Entrenador();
                joseRed.setNombre("Jose Red");
                joseRed.setUbicacion(elOrigen);
                DATA.put(joseRed.getNombre(), joseRed);

                elOrigen.agregarEntrenador(joseRed);
                ubicacionDaoMock.guardar(elOrigen);
            }

    @Override
    public void guardar(Entrenador entrenador) {
        DATA.put(entrenador.getNombre(), entrenador);
    }

    @Override
    public Entrenador recuperar(String nombreDelEntrenador) {
        return DATA.get(nombreDelEntrenador);
    }

    @Override
    public void actualizar(Entrenador entrenador) { DATA.put(entrenador.getNombre(), entrenador);   }
}
