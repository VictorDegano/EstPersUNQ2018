package ar.edu.unq.epers.bichomon.backend.service.bicho;

import ar.edu.unq.epers.bichomon.backend.dao.*;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.excepcion.BichoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.excepcion.BusquedaFallida;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.TipoExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Registro;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

import javax.persistence.NoResultException;

public class BichoServiceImplementacion implements BichoService
{
    private UbicacionDAO ubicacionDao;
    private EntrenadorDAO entrenadorDao;
    private BichoDAO bichoDao;
    private EspecieDAO especieDao;
    private ExperienciaDAO experienciaDao;

    /**
     * Busca un bicho en la ubicacion actual del entrenador especificado.
     *
     * @param entrenador - Nombre del entrenador que va a buscar un nuevo bicho.
     * @return El {@link Bicho} que fue capturado si fue exitoso.
     */
    @Override
    public Bicho buscar(String entrenador)
    {
       return Runner.runInSession(() -> {
                Entrenador unEntrenador = this.getEntrenadorDao().recuperar(entrenador);
                Bicho bicho = unEntrenador.buscarBicho();
                if (bicho != null )
                {
                   this.getEntrenadorDao().actualizar(unEntrenador);
                    unEntrenador.subirExperiencia(this.experienciaDao.recuperar(TipoExperiencia.CAPTURA).getExperiencia());
                }
                else
                {   throw new BusquedaFallida();    }
                return bicho;
            });
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
     * @throws BichoRecuperarException si no encuentra el bicho
     */
    @Override
    public boolean puedeEvolucionar(String entrenador, int bicho)
    {
        return Runner.runInSession(() -> {  Bicho unBicho   = this.getBichoDao().recuperar(bicho);
                                            if(unBicho == null)
                                            {   throw new BichoRecuperarException(bicho);   }
                                            return unBicho.puedeEvolucionar();});
    }

    /**
     * Evoluciona el bicho especificado del entrenador si cumple las condiciones de evolucion.
     *
     * @param entrenador - Nombre del entrenador del bicho
     * @param bicho      - Id del bicho que va a ser evolucionado
     * @return Un nuevo {@link Bicho} que es el resultante de la evolucion del bicho anterior.
     * @throws NoResultException si no encuentra el entrenador
     * @throws BichoRecuperarException si no encuentra el bicho
     */
    @Override
    public Bicho evolucionar(String entrenador, int bicho)
    {
        return Runner.runInSession(() -> {
                Bicho unBicho           = this.getBichoDao().recuperar(bicho);
                Entrenador unEntrenador = this.getEntrenadorDao().recuperar(entrenador);

                if(unBicho == null)
                {   throw new BichoRecuperarException(bicho);   }

                unBicho.evolucionar();
                unEntrenador.subirExperiencia(this.experienciaDao.recuperar(TipoExperiencia.EVOLUCION).getExperiencia());
                this.getEntrenadorDao().actualizar(unEntrenador);

                return unBicho;
            });
    }

    /**
     * El entrenador desafia al campeon del dojo.
     *
     * @param entrenador
     * @param bicho
     * @return ResultadoCombate un log donde se muestra lo sucedido en el combate
     * @throws UbicacionIncorrectaException si la ubicacion no es un dojo.
     */
    @Override
    public Registro duelo(String entrenador, int bicho)
    {
        return Runner.runInSession(() -> {
                    Bicho unBicho           = this.getBichoDao().recuperar(bicho);
                    Entrenador unEntrenador = this.getEntrenadorDao().recuperar(entrenador);
                    Registro registroDeBatalla;

                    if(unBicho == null)
                    {   throw new BichoRecuperarException(bicho);   }

                    registroDeBatalla   = unEntrenador.retar(unBicho);

                    if (registroDeBatalla.getGanador().getId() == unBicho.getId())
                    {
                        unEntrenador.subirExperiencia(this.experienciaDao.recuperar(TipoExperiencia.COMBATE).getExperiencia());
                        this.getEntrenadorDao().actualizar(unEntrenador);
                    }
                    this.getUbicacionDao().actualizar(unEntrenador.getUbicacion());

                    return registroDeBatalla;
                });
    }

    /*[--------]Constructors[--------]*/
    public BichoServiceImplementacion() {   }

    public BichoServiceImplementacion(EntrenadorDAOHibernate entrenadorDao, UbicacionDAOHibernate ubicacionDao, BichoDAOHibernate bichoDao, EspecieDAOHibernate especieDao, ExperienciaDAOHibernate experienciaDao)
    {
        this.entrenadorDao  = entrenadorDao;
        this.ubicacionDao   = ubicacionDao;
        this.bichoDao       = bichoDao;
        this.especieDao     = especieDao;
        this.experienciaDao = experienciaDao;
    }

    /*[--------]Getters & Setters[--------]*/
    public UbicacionDAO getUbicacionDao() { return ubicacionDao;    }
    public void setUbicacionDao(UbicacionDAO ubicacionDao) {    this.ubicacionDao = ubicacionDao;   }

    public EntrenadorDAO getEntrenadorDao() {   return entrenadorDao;   }
    public void setEntrenadorDao(EntrenadorDAO entrenadorDao) { this.entrenadorDao = entrenadorDao; }

    public BichoDAO getBichoDao() { return bichoDao;    }
    public void setBichoDao(BichoDAO bichoDao) {    this.bichoDao = bichoDao;   }

    public EspecieDAO getEspecieDao() { return especieDao;  }
    public void setEspecieDao(EspecieDAO especieDao) {  this.especieDao = especieDao;   }
}
