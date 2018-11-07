package ar.edu.unq.epers.bichomon.backend.mock;

import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta es una implementacion mock de UbicacionDAO para fines de construccion del modelo y services
 * sin involucrar a la BD
 */
public class UbicacionDAOMock implements UbicacionDAO
{
    private static Map<String, Ubicacion> DATA = new HashMap<>();

    static  {
        Ubicacion elOrigen = new Pueblo();
        elOrigen.setNombre("El Origen");
        DATA.put(elOrigen.getNombre(), elOrigen);

        Ubicacion unaUbicacion = new Pueblo();
        unaUbicacion.setNombre("Delta");
        DATA.put(unaUbicacion.getNombre(), unaUbicacion);

        Ubicacion unaUbicacionDojo = new Dojo();
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

    @Override
    public Bicho recuperarCampeonHistoricoDe(String dojo) {
        return null;
    }

    @Override
    public List<Ubicacion> recuperarUbicaciones(List<String> nombresDeUbicaciones) {
        return null;
    }
}
