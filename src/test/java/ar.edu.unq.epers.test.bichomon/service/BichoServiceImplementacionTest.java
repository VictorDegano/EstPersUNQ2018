package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.BichoDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class BichoServiceImplementacionTest {

    private Bootstrap bootstraper;
    private BichoServiceImplementacion bichoServiceSut;
    private EntrenadorDAOHibernate entrenadorDao;
    private UbicacionDAOHibernate ubicacionDao;
    private BichoDAOHibernate bichoDao;

    @Before
    public void setUp() throws Exception
    {
        bootstraper     = new Bootstrap();
        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    return null;});
        entrenadorDao   = new EntrenadorDAOHibernate();
        ubicacionDao    = new UbicacionDAOHibernate();
        bichoDao        = new BichoDAOHibernate();
        bichoServiceSut = new BichoServiceImplementacion(entrenadorDao, ubicacionDao, bichoDao);
    }

    @After
    public void tearDown() throws Exception
    {
        Runner.runInSession(()-> {  bootstraper.limpiarTabla();
                                    return null;});
    }

    @Test
    public void siSeAbandonaUnBichomonEnUnPuebloSeObtieneUnaExcepcionYNoOcurreNingunCambio()
    {
        //Setup(Given)
        Entrenador pepePepon;
        //Exercise(When)
        try
        {   bichoServiceSut.abandonar("Pepe Pepon",30); }
        catch(UbicacionIncorrectaException e)
        {   }
        finally
        {   pepePepon   = Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Pepon");});   }
        //Test(Then)
        assertFalse(pepePepon.getBichosCapturados().isEmpty());
    }

    @Test
    public void siSeAbandonaUnBichomonEnUnaGuarderiaElEntrenadorNoTieneMasAlBichomon()
    {
        //Setup(Given)
        MapaService unMapaService   = new MapaServiceImplementacion(entrenadorDao, ubicacionDao);
        unMapaService.mover("Pepe Pepon", "La Guarderia");
        Entrenador pepePepon;
        //Exercise(When)
        bichoServiceSut.abandonar("Pepe Pepon",1);
        pepePepon   = Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Pepon");});
        //Test(Then)
        assertTrue(pepePepon.getBichosCapturados().isEmpty());
    }

    @Test @Ignore
    public void buscar() {
    }

    @Test @Ignore
    public void puedeEvolucionar() {
    }

    @Test @Ignore
    public void evolucionar() {
    }
}