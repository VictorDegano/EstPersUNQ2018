package ar.edu.unq.epers.bichomon.backend.mock;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta es una implementacion mock de UbicacionDAO para fines de construccion del modelo y services
 * sin involucrar a la BD
 */
public class UbicacionDAOMock implements UbicacionDAO
{
    private static Map<String, Ubicacion> DATA = new HashMap<>();

    static  {
        Ubicacion elOrigen = new Ubicacion();
        elOrigen.setNombre("El Origen");
        DATA.put(elOrigen.getNombre(), elOrigen);

        Ubicacion unaUbicacion = new Ubicacion();
        unaUbicacion.setNombre("Delta");
        DATA.put(unaUbicacion.getNombre(), unaUbicacion);

        Ubicacion unaUbicacionDojo = new Ubicacion();
        unaUbicacionDojo.setNombre("Dojo Delta");
        DATA.put(unaUbicacionDojo.getNombre(), unaUbicacionDojo);
    }

    @Override
    public void guardar(Ubicacion unaUbicacion) {
        DATA.put(unaUbicacion.getNombre(), unaUbicacion);
    }

    @Override
    public Ubicacion recuperar(String nombre) {
        return DATA.get(nombre);
    }

    @Override
    public void actualizar(Ubicacion ubicacion)  { DATA.put(ubicacion.getNombre(), ubicacion);}
}
