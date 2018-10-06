package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.excepcion.BichoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEnergia;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionNivel;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionVictoria;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BichoServiceImplementacionTest {

    private Bootstrap bootstraper;
    private BichoServiceImplementacion bichoServiceSut;
    private EntrenadorDAOHibernate entrenadorDao;
    private UbicacionDAOHibernate ubicacionDao;
    private BichoDAOHibernate bichoDao;
    private EspecieDAOHibernate especieDao;
    private CondicionDeEvolucionDAOHibernate condicionDao;

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
        condicionDao    = new CondicionDeEvolucionDAOHibernate();
        especieDao      = new EspecieDAOHibernate();
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
        pepePepon               = Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Pepon");});
        Bicho bichoAbandonado   = Runner.runInSession(()-> { return bichoDao.recuperar(1);});
        //Test(Then)
        assertFalse(pepePepon.getBichosCapturados().contains(bichoAbandonado));
        assertEquals(1, pepePepon.getBichosCapturados().size());
    }

    @Test
    public void SiUnBichoNoCumpleTodasLasCondicionesParaEvolucionarYSuEspecieTieneEvolucionNoPuedeEvolucionar()
    {
        //Setup(Given)
        setUpBichoSinCumplirCondicion();
        //Exercise(When)
        //Test(Then)
        assertFalse(bichoServiceSut.puedeEvolucionar("Pepe Enpepado", 12));
    }

    @Test
    public void SiUnBichoCumpleLasCondicionesParaEvolucionarYSuEspecieTieneEvolucionPuedeEvolucionar()
    {
        //Setup(Given)
        setUpBichoCumpleCondicion();
        //Exercise(When)
        //Test(Then)
        assertTrue(bichoServiceSut.puedeEvolucionar("Pepe Enpepado", 24));
    }

    @Test(expected = BichoRecuperarException.class)
    public void SiSeIntentaVerificarQueUnBichoInexistentePuedeEvolucionarHayUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        bichoServiceSut.puedeEvolucionar("Pepe Enpepado", 66);
        //Test(Then)
        fail("No Hubo Excepcion");
    }

    @Test
    public void SiUnBichoNoTieneCondicionesParaEvolucionarYSuEspecieTieneEvolucionPuedeEvolucionar()
    {
        //Setup(Given)
        setUpBichoSinCondicion();
        //Exercise(When)
        //Test(Then)
        assertTrue(bichoServiceSut.puedeEvolucionar("Pepe Enpepado", 24));
    }

    @Test @Ignore
    public void buscar() {
    }



    @Test @Ignore
    public void evolucionar() {
    }

    private void setUpBichoSinCondicion()
    {   setUpBichoPuedeEvolucionar(Collections.emptyList());    }

    private void setUpBichoCumpleCondicion()
    {
        CondicionEnergia condicion1 = new CondicionEnergia(80);
        CondicionVictoria condicion2= new CondicionVictoria(1);
        Runner.runInSession(()-> {  condicionDao.guardar(condicion1);
                                    condicionDao.guardar(condicion2);
                                    return null;});
        setUpBichoPuedeEvolucionar(Arrays.asList( condicion1, condicion2));
    }

    private void setUpBichoSinCumplirCondicion()
    {
        CondicionEnergia condicion1 = new CondicionEnergia(300);
        CondicionVictoria condicion2= new CondicionVictoria(33);
        Runner.runInSession(()-> {  condicionDao.guardar(condicion1);
                                    condicionDao.guardar(condicion2);
                                    return null;});
        setUpBichoPuedeEvolucionar(Arrays.asList( condicion1, condicion2));
    }

    private void setUpBichoPuedeEvolucionar(List<CondicionEvolucion> listaDeCondiciones)
    {
        Especie lagartomon  = new Especie();
        lagartomon.setNombre("Lagartomon");
        lagartomon.setTipo(TipoBicho.TIERRA);
        lagartomon.setAltura(20);
        lagartomon.setPeso(22);
        lagartomon.setEnergiaIncial(100);
        lagartomon.setUrlFoto("/image/Lagartomon.jpg");
        lagartomon.setCantidadBichos(1);
        lagartomon.setEspecieBase(lagartomon);
        lagartomon.setCondicionesDeEvolucion(listaDeCondiciones);

        Especie reptilmon   = new Especie();
        reptilmon.setNombre("Reptilmon");
        reptilmon.setTipo(TipoBicho.TIERRA);
        reptilmon.setAltura(50);
        reptilmon.setPeso(42);
        reptilmon.setEnergiaIncial(500);
        reptilmon.setUrlFoto("/image/Reptilmon.jpg");
        reptilmon.setCantidadBichos(0);
        reptilmon.setEspecieBase(lagartomon);
        lagartomon.setEvolucion(reptilmon);

        Bicho lagortito = new Bicho();
        lagortito.setEspecie(lagartomon);
        lagortito.setEnergia(lagartomon.getEnergiaInicial());
        lagortito.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now().minusDays(30)));
        lagortito.setVictorias(10);

        Entrenador entrenadorPepe  = new Entrenador();
        entrenadorPepe.setNombre("Pepe Enpepado");
        entrenadorPepe.setExperiencia(25);
        entrenadorPepe.setNivel(new Nivel(4, 1000, 1999, 7));
        entrenadorPepe.setBichosCapturados(Arrays.asList(lagortito));

        lagortito.setDuenio(entrenadorPepe);

        Runner.runInSession(()-> {  especieDao.guardar(lagartomon);
                                    especieDao.guardar(reptilmon);
                                    bichoDao.guardar(lagortito);
                                    entrenadorDao.guardar(entrenadorPepe);
                                    return null;});
    }
}