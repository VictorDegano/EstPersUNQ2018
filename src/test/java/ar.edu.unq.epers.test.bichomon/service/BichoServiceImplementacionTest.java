package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.*;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.excepcion.BichoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.excepcion.EvolucionException;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEnergia;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionVictoria;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Registro;
import ar.edu.unq.epers.bichomon.backend.service.bicho.BichoServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaService;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import extra.BootstrapNeo4J;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BichoServiceImplementacionTest {

    private Bootstrap bootstraper;
    private BootstrapNeo4J bootstraperNeo4j;
    private MapaService mapaService;
    private BichoServiceImplementacion bichoServiceSut;
    private EntrenadorDAOHibernate entrenadorDao;
    private UbicacionDAOHibernate ubicacionDao;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private BichoDAOHibernate bichoDao;
    private EspecieDAOHibernate especieDao;
    private CondicionDeEvolucionDAOHibernate condicionDao;
    private ExperienciaDAOHibernate experienciaDao;
    private Especie lagartomon;
    private Especie reptilmon;
    private Bicho lagortito;

    @Before
    public void setUp() throws Exception
    {
        bootstraper       = new Bootstrap();
        bootstraperNeo4j  = new BootstrapNeo4J();
        ubicacionDAONEO4J = new UbicacionDAONEO4J();
        ubicacionDao      = new UbicacionDAOHibernate();
        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    ubicacionDAONEO4J.create(ubicacionDao.recuperar("El Origen"));
                                    ubicacionDAONEO4J.create(ubicacionDao.recuperar("Dojo Desert"));
                                    ubicacionDAONEO4J.create(ubicacionDao.recuperar("La Guarderia"));
                                    ubicacionDAONEO4J.conectar("El Origen", "La Guarderia", TipoCamino.TERRESTRE);
                                    ubicacionDAONEO4J.conectar("El Origen", "Dojo Desert", TipoCamino.TERRESTRE);
                                    return null;});
        entrenadorDao     = new EntrenadorDAOHibernate();

        bichoDao          = new BichoDAOHibernate();
        especieDao        = new EspecieDAOHibernate();
        experienciaDao    = new ExperienciaDAOHibernate();
        bichoServiceSut   = new BichoServiceImplementacion(entrenadorDao, ubicacionDao, bichoDao, especieDao, experienciaDao);
        condicionDao      = new CondicionDeEvolucionDAOHibernate();
        mapaService       = new MapaServiceImplementacion(entrenadorDao, ubicacionDao, ubicacionDAONEO4J);
    }

    @After
    public void tearDown() throws Exception
    {bootstraper.limpiarTabla();
                                    bootstraperNeo4j.limpiarTabla();
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
        MapaService unMapaService   = new MapaServiceImplementacion(entrenadorDao, ubicacionDao,ubicacionDAONEO4J);
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

    @Test(expected = BichoRecuperarException.class)
    public void SiSeIntentaEvolucionarUnBichoInexistenteNosDaUnaExcepcion()
    {
        //Setup(Given)
        setUpBichoCumpleCondicion();
        Bicho bichoEvolucionado;
        //Exercise(When)
        bichoEvolucionado   = bichoServiceSut.evolucionar("Pepe Enpepado", 19092);
        //Test(Then)
    }

    @Test(expected = NoResultException.class)
    public void SiSeIntentaEvolucionarAUnBichoDeUnEntrenadorInexistenteNosDaUnaExcepcion()
    {
        //Setup(Given)
        setUpBichoCumpleCondicion();
        Bicho bichoEvolucionado;
        //Exercise(When)
        bichoEvolucionado   = bichoServiceSut.evolucionar("Pepe Enpepado Super Fiesta", 12);
        //Test(Then)
    }

    @Test
    public void SiUnBichoCumpleConLasCondicionesParaEvolucionarEvoluciona()
    {
        //Setup(Given)
        Bicho bichoEvolucionado;
        Bicho bichoRecuperado;
        Entrenador entrenadorRecuperado;
        Especie especieVieja;
        Especie especieNueva;
        setUpBichoCumpleCondicion();
        //Exercise(When)
        bichoEvolucionado   = bichoServiceSut.evolucionar("Pepe Enpepado", 24);
        bichoRecuperado     = Runner.runInSession(()-> { return bichoDao.recuperar(24);});
        entrenadorRecuperado= Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Enpepado");});
        especieVieja        = Runner.runInSession(()-> { return especieDao.recuperar("Lagartomon");});
        especieNueva        = Runner.runInSession(()-> { return especieDao.recuperar("Reptilmon");});
        //Test(Then)
        assertNotEquals(lagortito.getEspecie(), bichoEvolucionado.getEspecie());
        assertEquals(bichoEvolucionado.getEspecie().getId(), bichoRecuperado.getEspecie().getId());
        assertEquals(bichoEvolucionado.getEspecie().getNombre(), bichoRecuperado.getEspecie().getNombre());
        assertTrue(entrenadorRecuperado .getBichosCapturados()
                                        .stream()
                                        .anyMatch(bichos-> (bichos.getId() == bichoRecuperado.getId()) && (bichos.getEspecie().getId() == bichoRecuperado.getEspecie().getId())));
        assertTrue(entrenadorRecuperado .getBichosCapturados()
                                        .stream()
                                        .noneMatch(bichos-> (bichos.getId() == lagortito.getId()) && (bichos.getEspecie().getId() == lagortito.getEspecie().getId())));
        assertEquals(30, entrenadorRecuperado.getExperiencia());
        assertEquals(0, especieVieja.getCantidadBichos());
        assertEquals(1, especieNueva.getCantidadBichos());
    }

    @Test
    public void SiUnBichoNoCumpleConLasCondicionesParaEvolucionarNoEvoluciona()
    {
        //Setup(Given)
        Bicho bichoEvolucionado;
        Bicho bichoRecuperado;
        Entrenador entrenadorRecuperado;
        Especie especieVieja;
        Especie especieNueva;
        setUpBichoSinCumplirCondicion();
        //Exercise(When)
        try
        {   bichoEvolucionado   = bichoServiceSut.evolucionar("Pepe Enpepado", 24); }
        catch (EvolucionException e)
        {   bichoEvolucionado   = Runner.runInSession(()-> { return bichoDao.recuperar(24);});  }
        bichoRecuperado     = Runner.runInSession(()-> { return bichoDao.recuperar(24);});
        entrenadorRecuperado= Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Enpepado");});
        especieVieja        = Runner.runInSession(()-> { return especieDao.recuperar("Lagartomon");});
        especieNueva        = Runner.runInSession(()-> { return especieDao.recuperar("Reptilmon");});
        //Test(Then)
        assertNotEquals(lagortito.getEspecie(), bichoEvolucionado.getEspecie());
        assertEquals(bichoEvolucionado.getEspecie().getId(), bichoRecuperado.getEspecie().getId());
        assertEquals(bichoEvolucionado.getEspecie().getNombre(), bichoRecuperado.getEspecie().getNombre());
        assertTrue(entrenadorRecuperado .getBichosCapturados()
                                        .stream()
                                        .anyMatch(bichos->  (bichos.getId() == bichoRecuperado.getId())
                                                            && (bichos.getEspecie().getId() == especieVieja.getId())));
        assertTrue(entrenadorRecuperado .getBichosCapturados()
                                        .stream()
                                        .noneMatch(bichos-> (bichos.getId() == lagortito.getId())
                                                            && (bichos.getEspecie().getId() == especieVieja.getEvolucion().getId())));
        assertEquals(25, entrenadorRecuperado.getExperiencia());
        assertEquals(1, especieVieja.getCantidadBichos());
        assertEquals(0, especieNueva.getCantidadBichos());
    }

    @Test(expected = UbicacionIncorrectaException.class)
    public void SiUnRetadorIntentaHacerUnDueloEnUnPuebloDaError()
    {
        //Setup(Given)
        //Exercise(When)
        bichoServiceSut.duelo("Pepe Pepon", 2);
        //Test(Then)
    }

    @Test(expected = UbicacionIncorrectaException.class)
    public void SiUnRetadorIntentaHacerUnDueloEnUnaGuarderiaDaError()
    {
        //Setup(Given)
        mapaService.mover("Pepe Pepon","La Guarderia");
        //Exercise(When)
        bichoServiceSut.duelo("Pepe Pepon", 2);
        //Test(Then)
    }

    @Test
    public void SiUnRetadorRetaAUnDojoSinCampeonElBichoQueUsaSeConvierteEnCampeon()
    {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        Registro registro;
        Dojo ubicacionRecuperada;
        //Exercise(When)
        mapaService.mover("Pepe Pepon", "Dojo Desert");
        registro            = bichoServiceSut.duelo("Pepe Pepon", 2);
        entrenadorRecuperado= Runner.runInSession(()-> { return entrenadorDao.recuperar("Pepe Pepon");});
        ubicacionRecuperada = (Dojo) Runner.runInSession(()-> { return ubicacionDao.recuperar("Dojo Desert");});

        //Test(Then)
        assertEquals(10, entrenadorRecuperado.getExperiencia());
        assertEquals(2, ubicacionRecuperada.campeonActual().getId());
        assertEquals(1, ubicacionRecuperada.getHistorial().size());
        assertEquals(2, ubicacionRecuperada.getHistorial().get(0).getGanador().getId());
    }

    @Test
    public void buscar() {
        Entrenador entrenadorRecuperado;
        Bicho bichoEncontrado;
        bichoEncontrado = Runner.runInSession(()-> {return bichoServiceSut.buscar("Marcelo");});
        entrenadorRecuperado = Runner.runInSession(()-> {return entrenadorDao.recuperar("Marcelo"); });
        assertEquals(bichoEncontrado.getEnergia(),5000);
        assertEquals(bichoEncontrado.getDuenio().getNombre(),entrenadorRecuperado.getNombre());

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
        lagartomon  = new Especie();
        lagartomon.setNombre("Lagartomon");
        lagartomon.setTipo(TipoBicho.TIERRA);
        lagartomon.setAltura(20);
        lagartomon.setPeso(22);
        lagartomon.setEnergiaIncial(100);
        lagartomon.setUrlFoto("/image/Lagartomon.jpg");
        lagartomon.setCantidadBichos(1);
        lagartomon.setEspecieBase(lagartomon);
        lagartomon.setCondicionesDeEvolucion(listaDeCondiciones);

        reptilmon   = new Especie();
        reptilmon.setNombre("Reptilmon");
        reptilmon.setTipo(TipoBicho.TIERRA);
        reptilmon.setAltura(50);
        reptilmon.setPeso(42);
        reptilmon.setEnergiaIncial(500);
        reptilmon.setUrlFoto("/image/Reptilmon.jpg");
        reptilmon.setCantidadBichos(0);
        reptilmon.setEspecieBase(lagartomon);
        lagartomon.setEvolucion(reptilmon);

        lagortito = new Bicho();
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