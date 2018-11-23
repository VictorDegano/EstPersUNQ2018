package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.excepcion.CaminoMuyCostoso;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionMuyLejanaException;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import extra.BootstrapNeo4J;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class MapaServiceImplementacionTest
{
    private MapaServiceImplementacion mapaServiceSUT;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private Ubicacion unaUbicacion;
    private Ubicacion unaUbicacionNueva;
    private Ubicacion dojoDeshabitado;
    private Entrenador pepePrueba;
    private Entrenador pepeEmpepeado;
    private Bootstrap bootstraper;
    private BootstrapNeo4J bootstraperneo4j;
    private EventoDAO eventoDAOMongoDB;

    @Before
    public void setUp() throws Exception
    {
        bootstraper         = new Bootstrap();
        bootstraperneo4j    = new BootstrapNeo4J();
        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    bootstraperneo4j.crearDatos();
                                    return null;});

        entrenadorDAO       = new EntrenadorDAOHibernate();
        ubicacionDAO        = new UbicacionDAOHibernate();
        ubicacionDAONEO4J   = new UbicacionDAONEO4J();
        eventoDAOMongoDB    = new EventoDAOMongoDB();
        mapaServiceSUT      = new MapaServiceImplementacion(entrenadorDAO, ubicacionDAO, ubicacionDAONEO4J, eventoDAOMongoDB);
        pepePrueba          = new Entrenador();
        unaUbicacion        = new Pueblo();
        unaUbicacion.setNombre("El Origen 2");
        unaUbicacionNueva   = new Pueblo();
        unaUbicacionNueva.setNombre("Volcano");
        dojoDeshabitado     = new Dojo();
        dojoDeshabitado.setNombre("Dojo Deshabitado");

        pepePrueba.setNombre("Pepe DePrueba");
        pepePrueba.setUbicacion(unaUbicacion);
        pepePrueba.setBilletera(5);
        unaUbicacion.agregarEntrenador(pepePrueba);

        Ubicacion puebloOrigen  = Runner.runInSession(()-> {    return ubicacionDAO.recuperar("Pueblo Origen"); });
        pepeEmpepeado           = new Entrenador();
        pepeEmpepeado.setNombre("Pepe Empepado");
        pepeEmpepeado.setUbicacion(puebloOrigen);
        pepeEmpepeado.setBilletera(1);
        puebloOrigen.agregarEntrenador(pepeEmpepeado);

        Runner.runInSession(()-> {  entrenadorDAO.guardar(pepePrueba);
                                    ubicacionDAO.guardar(unaUbicacion);
                                    ubicacionDAO.guardar(unaUbicacionNueva);
                                    ubicacionDAO.guardar(dojoDeshabitado);
                                    entrenadorDAO.guardar(pepeEmpepeado);
                                    ubicacionDAO.guardar(puebloOrigen);
                                    this.ubicacionDAONEO4J.create(unaUbicacion);
                                    this.ubicacionDAONEO4J.create(dojoDeshabitado);
                                    this.ubicacionDAONEO4J.create(unaUbicacionNueva);
                                    this.ubicacionDAONEO4J.conectar("El Origen 2", "Dojo Deshabitado", TipoCamino.TERRESTRE);
                                    this.ubicacionDAONEO4J.conectar("Dojo Deshabitado", "Volcano", TipoCamino.TERRESTRE);
                                    return null; });


    }
    @After
    public void tearDown() throws Exception
    {
        bootstraper.limpiarTabla();
        bootstraperneo4j.limpiarTabla();
        eventoDAOMongoDB.deleteAll();
    }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnaNuevaUbicacionSeActualizanSusDatos()
    {
        //Setup(Given)
        Ubicacion ubicacionNuevaBD;
        Ubicacion ubicacionViejaBD;
        Entrenador entrenador;
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Dojo Deshabitado");
        ubicacionNuevaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("Dojo Deshabitado");});
        ubicacionViejaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("El Origen 2");});
        entrenador      = Runner.runInSession(() -> { return entrenadorDAO.recuperar("Pepe DePrueba");});

        //Test(Then)
        assertFalse(ubicacionNuevaBD.getEntrenadores().isEmpty());
        assertEquals(1, ubicacionNuevaBD.getEntrenadores().size());
        assertTrue(ubicacionViejaBD.getEntrenadores().isEmpty());
        assertEquals("Dojo Deshabitado", entrenador.getUbicacion().getNombre() );
        assertEquals(4, entrenador.getBilletera());
    }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnDojoSeActualizanSusDatos()
    {
        //Setup(Given)
        Ubicacion dojoBD;
        Ubicacion ubicacionViejaBD;
        Entrenador entrenador;
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Dojo Deshabitado");
        dojoBD          = Runner.runInSession(() -> { return ubicacionDAO.recuperar("Dojo Deshabitado");});
        ubicacionViejaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("El Origen 2");});
        entrenador      = Runner.runInSession(() -> { return entrenadorDAO.recuperar("Pepe DePrueba");});

        //Test(Then)
        assertFalse(dojoBD.getEntrenadores().isEmpty());
        assertEquals(1, dojoBD.getEntrenadores().size());
        assertTrue(ubicacionViejaBD.getEntrenadores().isEmpty());
        assertEquals("Dojo Deshabitado", entrenador.getUbicacion().getNombre() );
    }

    @Test(expected = NoResultException.class)
    public void siElMapaServiceIntentaMoverAUnEntrenadorAUnaUbicacionQueNoExisteTiraUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcanos");

        //Test(Then)
    }

    @Test(expected = NoResultException.class)
    public void siElMapaServiceIntentaMoverAUnEntrenadorQueNoExisteTiraUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe", "Volcano");

        //Test(Then)
    }

    @Test
    public void siOcurreUnaExcepcionAlMoverElEntrenadorLasUbicacionesNoSonAfectadas()
    {
        //Setup(Given)
        String mensajeDeError    = "";
        Ubicacion ubicacionViejaBD;
        Entrenador entrenador;
        //Exercise(When)
        try
        {   mapaServiceSUT.mover("Pepe DePrueba", "Volcanos");    }
        catch (NoResultException e)
        {   mensajeDeError  = e.getMessage();   }
        ubicacionViejaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("El Origen 2");});
        entrenador      = Runner.runInSession(() -> { return entrenadorDAO.recuperar("Pepe DePrueba");});

        //Test(Then)
        assertFalse(ubicacionViejaBD.getEntrenadores().isEmpty());
        assertEquals("El Origen 2", entrenador.getUbicacion().getNombre() );
        assertEquals("No entity found for query", mensajeDeError);
    }

    @Test
    public void siSePideLaCantidadEntrenadoresEnUnaUbicacionExistenteQueNoTieneEntrenadoresDevuelve0()
    {
        //Setup(Given)
        int cantidadDeEntrenadores;
        //Exercise(When)
        cantidadDeEntrenadores = Runner.runInSession(() -> { return mapaServiceSUT.cantidadEntrenadores("Volcano");});
        //Test(Then)
        assertEquals(0, cantidadDeEntrenadores);
    }

    @Test
    public void siSePideLaCantidadEntrenadoresEnUnaUbicacionExistenteQueTieneEntrenadoresDevuelveLaCantidadCorrespondiente()
    {
        //Setup(Given)
        int cantidadDeEntrenadores;
        //Exercise(When)
        cantidadDeEntrenadores  = Runner.runInSession(() -> { return mapaServiceSUT.cantidadEntrenadores("El Origen 2");});
        //Test(Then)
        assertEquals(1, cantidadDeEntrenadores);
    }

    @Test
    public void siSePideLaCantidadEntrenadoresEnUnaUbicacionInexistenteHayUnaExcepcion()
    {
        //Setup(Given)
        String mensajeDeError    = "";
        //Exercise(When)
        try
        {   mapaServiceSUT.cantidadEntrenadores("Missing Field");    }
        catch (NoResultException e)
        {   mensajeDeError  = e.getMessage();   }

        //Test(Then)
        assertEquals("No entity found for query", mensajeDeError);
    }

    @Test
    public void SiPidoElCampeonActualAUnDojoSinCampeonMeDaNull()
    {
        //Setup(Given)
        //Exercise(When)
        Bicho campeon   = mapaServiceSUT.campeon("Dojo Deshabitado");
        //Test(Then)
        assertNull(campeon);
    }

    @Test
    public void SiPidoElCampeonActualAUnDojoQueTieneCampeonMeLoDa()
    {
        //Setup(Given)
        Bicho campeonActual;
        Dojo dojoConCampeon     = new Dojo();
        dojoConCampeon.setNombre("The Last Dojo");
        Especie fortmon         = Runner.runInSession(() -> {   EspecieDAOHibernate especieDAOHibernate = new EspecieDAOHibernate();
                                                            return especieDAOHibernate.recuperar("Fortmon");});
        Bicho bichoCampeon      = new Bicho(fortmon, "");
        Campeon fortimonCampeon = new Campeon();
        fortimonCampeon.setBichoCampeon(bichoCampeon);
        fortimonCampeon.setFechaInicioDeCampeon(Timestamp.valueOf("2018-09-16 15:00:00"));
        dojoConCampeon.setCampeonActual(fortimonCampeon);

        Runner.runInSession(() -> { ubicacionDAO.guardar(dojoConCampeon);
                                    return null;});

        //Exercise(When)
        campeonActual   = mapaServiceSUT.campeon("The Last Dojo");
        //Test(Then)
        assertNotNull(campeonActual);
    }

    @Test
    public void campeonHistorico() {

        //Setup(Given)
        Bicho bicho1;
        Bicho bicho2;
        Bicho bicho3;

        Campeon campeon1;
        Campeon campeon2;
        Campeon campeon3;

        Especie fortmon = Runner.runInSession(() -> {
            EspecieDAOHibernate especieDAOHibernate = new EspecieDAOHibernate();
            return especieDAOHibernate.recuperar("Fortmon");
        });
        Especie tierramon = Runner.runInSession(() -> {
            EspecieDAOHibernate especieDAOHibernate = new EspecieDAOHibernate();
            return especieDAOHibernate.recuperar("Tierramon");
        });
        Especie miguelmon = Runner.runInSession(() -> {
            EspecieDAOHibernate especieDAOHibernate = new EspecieDAOHibernate();
            return especieDAOHibernate.recuperar("Miguelmon");
        });

        bicho1 = new Bicho(fortmon, "1");
        bicho2 = new Bicho(tierramon, "2");
        bicho3 = new Bicho(miguelmon, "3");

        campeon1 = new Campeon();
        campeon1.setBichoCampeon(bicho1);
        campeon1.setFechaInicioDeCampeon(Timestamp.valueOf("2018-09-20 15:00:00"));
        campeon2 = new Campeon();
        campeon2.setBichoCampeon(bicho2);
        campeon2.setFechaInicioDeCampeon(Timestamp.valueOf("2018-09-17 15:00:00"));
        campeon2.setFechaFinDeCampeon(Timestamp.valueOf("2018-09-20 15:00:00"));
        campeon3 = new Campeon();
        campeon3.setBichoCampeon(bicho3);
        campeon3.setFechaInicioDeCampeon(Timestamp.valueOf("2018-09-16 15:00:00"));
        campeon3.setFechaFinDeCampeon(Timestamp.valueOf("2018-09-17 15:00:00"));

        Dojo dojo = new Dojo();
        dojo.setNombre("The Last Dojo");
        campeon1.setDojo(dojo);
        campeon2.setDojo(dojo);
        campeon3.setDojo(dojo);
        dojo.setCampeonActual(campeon1);
        dojo.campeonesHistoricos.add(campeon3);
        dojo.campeonesHistoricos.add(campeon2);



        Runner.runInSession(() -> { ubicacionDAO.guardar(dojo);
            return null;});

        //Exercise(When)
        Bicho campeonHistorico   = mapaServiceSUT.campeonHistorico("The Last Dojo");
        //Test(Then)
        assertEquals("Fortmon",campeonHistorico.getEspecie().getNombre());

    }


    @Test(expected = CaminoMuyCostoso.class)
    public void SiIntentoMoverAUnEntrenadorDeUnaUbicacionAOtraYNoTieneMonedasSuficientesDaUnaExcepcion()
    {
        //Setup(given)
        //Exercise(when)
        this.mapaServiceSUT.mover("Pepe Empepado","La Guarderia");
        //Test (Then)
    }

    @Test(expected = UbicacionMuyLejanaException.class)
    public void SiIntentoMoverAUnEntrenadorDeUnaUbicacionAOtraYLlevaMasDeUnCaminoLlegarDaUnaExcepcion()
    {
        //Setup(given)
        //Exercise(when)
        this.mapaServiceSUT.mover("Pepe Empepado","Dojo Lavanda");
        //Test (Then)
    }

    @Test
    public void PuedoMoverUnEntrenadorMasDeUnaUbicacionConMoverMasCorto()
    {

        Runner.runInSession(() -> { Entrenador pepe = entrenadorDAO.recuperar("Pepe Empepado");
                                    pepe.setBilletera(10);
                                    entrenadorDAO.guardar(pepe);
                                    return null;});

        this.mapaServiceSUT.moverMasCorto("Pepe Empepado","Dojo Lavanda");

        Entrenador entrenador = Runner.runInSession(() -> { return this.entrenadorDAO.recuperar("Pepe Empepado");});


        assertEquals(entrenador.getUbicacion().getNombre(), "Dojo Lavanda");
        assertTrue(entrenador.getBilletera() == 4 || entrenador.getBilletera() == 8);
    }

    @Test(expected = CaminoMuyCostoso.class)
    public void NoPuedoMoverUnEntrenadorMasDeUnaUbicacionConMoverMasCortoSiElCostoDeTodosLosViajesSuperanLaBilleteraDelEntrenador()
    {
        this.mapaServiceSUT.moverMasCorto("Pepe Empepado","Dojo Lavanda");
    }

    @Test
    public void siUnEntrenadorSeMueveSeGeneraUnEventoDeArribo()
    {
        //Setup(Given)
        List<Evento> eventoDeMovimiento;
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Dojo Deshabitado");
        eventoDeMovimiento  = eventoDAOMongoDB.feedDeEntrenador("Pepe DePrueba");

        //Test(Then)
        assertEquals(1, eventoDeMovimiento.size());
        assertEquals("Pepe DePrueba", eventoDeMovimiento.get(0).getEntrenador());
        assertEquals("Dojo Deshabitado", eventoDeMovimiento.get(0).getUbicacion());
        assertEquals("El Origen 2", eventoDeMovimiento.get(0).getUbicacionPartida());
    }

    @Test
    public void siUnEntrenadorSeMueveAUnaUbicacionPorElCaminoMasCortoSeGeneranLosEventoDeArriboPorCadaCaminoTransitado()
    {
        //Setup(Given)
        List<Evento> eventoDeMovimiento;
        //Exercise(When)
        mapaServiceSUT.moverMasCorto("Pepe DePrueba", "Volcano");
        eventoDeMovimiento  = eventoDAOMongoDB.feedDeEntrenador("Pepe DePrueba");

        //Test(Then)
        assertEquals(2, eventoDeMovimiento.size());
        assertEquals("Pepe DePrueba", eventoDeMovimiento.get(0).getEntrenador());
        assertEquals("El Origen 2", eventoDeMovimiento.get(0).getUbicacionPartida());
        assertEquals("Dojo Deshabitado", eventoDeMovimiento.get(0).getUbicacion());
        assertEquals("Pepe DePrueba", eventoDeMovimiento.get(1).getEntrenador());
        assertEquals("Dojo Deshabitado", eventoDeMovimiento.get(1).getUbicacionPartida());
        assertEquals("Volcano", eventoDeMovimiento.get(1).getUbicacion());
    }

}