package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoServiceImplementacion implements BichoService
{
    private UbicacionDAO ubicacionDao;
    private EntrenadorDAO entrenadorDao;
    private BichoDAO bichoDao;

    /**
     * Busca un bicho en la ubicacion actual del entrenador especificado.
     *
     * @param entrenador - Nombre del entrenador que va a buscar un nuevo bicho.
     * @return El {@link Bicho} que fue capturado si fue exitoso.
     */
    @Override
    public Bicho buscar(String entrenador) {
        return null;
    }

    /**
     * El entrenador abandona al bicho en la ubicacion actual. Si la ubicacion no es una Guarderia tira UbicacionIncorrectaException
     * @param entrenador - Nombre del entrenador que va a reliazar el abandono
     * @param bicho      - Id del bicho que va a ser abandonado
     * @throws UbicacionIncorrectaException si la ubicacion no es una guarderia.
     */
    @Override
    public void abandonar(String entrenador, int bicho)
    {
        Runner.runInSession(() -> {
                                    Bicho unBicho           = this.getBichoDao().recuperar(bicho);
                                    Entrenador unEntrenador = this.getEntrenadorDao().recuperar(entrenador);
                                    unEntrenador.abandonarBicho(unBicho);
                                    this.getEntrenadorDao().actualizar(unEntrenador);
                                    return null;
                                  });
    }

    /**
     * Retorna true si el bicho puede evolucionar.
     *
     * @param entrenador - Nombre del entrenador del bicho.
     * @param bicho      - Id del bicho que se va a consultar sobre su estado de evolucion
     * @return true si el bicho puede evolucionar o false en el caso contrario
     */
    @Override
    public boolean puedeEvolucionar(String entrenador, int bicho) {
        return false;
    }

    /**
     * Evoluciona el bicho especificado del entrenador si cumple las condiciones de evolucion.
     *
     * @param entrenador - Nombre del entrenador del bicho
     * @param Bicho      - Id del bicho que va a ser evolucionado
     * @return Un nuevo {@link Bicho} que es el resultante de la evolucion del bicho anterior.
     */
    @Override
    public Bicho evolucionar(String entrenador, int Bicho) {
        return null;
    }

/*[--------]Constructors[--------]*/
    public BichoServiceImplementacion(EntrenadorDAO entrenadorDao, UbicacionDAO ubicacionDao, BichoDAO bichoDao)
    {
        this.entrenadorDao  = entrenadorDao;
        this.ubicacionDao   = ubicacionDao;
        this.bichoDao       = bichoDao;
    }

/*[--------]Getters & Setters[--------]*/
    public UbicacionDAO getUbicacionDao() { return ubicacionDao;    }
    public void setUbicacionDao(UbicacionDAO ubicacionDao) {    this.ubicacionDao = ubicacionDao;   }

    public EntrenadorDAO getEntrenadorDao() {   return entrenadorDao;   }
    public void setEntrenadorDao(EntrenadorDAO entrenadorDao) { this.entrenadorDao = entrenadorDao; }

    public BichoDAO getBichoDao() { return bichoDao;    }
    public void setBichoDao(BichoDAO bichoDao) {    this.bichoDao = bichoDao;   }
}
