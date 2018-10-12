package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class MapaServiceImplementacionTest
{
    private MapaServiceImplementacion mapaServiceSUT;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private Ubicacion unaUbicacion;
    private Ubicacion unaUbicacionNueva;
    private Ubicacion dojoDeshabitado;
    private Entrenador pepePrueba;
    private Bootstrap bootstraper;

    @Before
    public void setUp() throws Exception
    {
        bootstraper         = new Bootstrap();
        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    return null;});

        entrenadorDAO       = new EntrenadorDAOHibernate();
        ubicacionDAO        = new UbicacionDAOHibernate();
        mapaServiceSUT      = new MapaServiceImplementacion(entrenadorDAO, ubicacionDAO);
        pepePrueba          = new Entrenador();
        unaUbicacion        = new Pueblo();
        unaUbicacion.setNombre("El Origen 2");
        unaUbicacionNueva   = new Pueblo();
        unaUbicacionNueva.setNombre("Volcano");
        dojoDeshabitado     = new Dojo();
        dojoDeshabitado.setNombre("Dojo Deshabitado");

        pepePrueba.setNombre("Pepe DePrueba");
        pepePrueba.setUbicacion(unaUbicacion);
        unaUbicacion.agregarEntrenador(pepePrueba);

        Runner.runInSession(()-> {  entrenadorDAO.guardar(pepePrueba);
                                    ubicacionDAO.guardar(unaUbicacion);
                                    ubicacionDAO.guardar(unaUbicacionNueva);
                                    ubicacionDAO.guardar(dojoDeshabitado);
                                    return null; });
    }
    @After
    public void tearDown() throws Exception
    {   bootstraper.limpiarTabla(); }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnaNuevaUbicacionSeActualizanSusDatos()
    {
        //Setup(Given)
        Ubicacion ubicacionNuevaBD;
        Ubicacion ubicacionViejaBD;
        Entrenador entrenador;
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcano");
        ubicacionNuevaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("Volcano");});
        ubicacionViejaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("El Origen 2");});
        entrenador      = Runner.runInSession(() -> { return entrenadorDAO.recuperar("Pepe DePrueba");});

        //Test(Then)
        assertFalse(ubicacionNuevaBD.getEntrenadores().isEmpty());
        assertEquals(1, ubicacionNuevaBD.getEntrenadores().size());
        assertTrue(ubicacionViejaBD.getEntrenadores().isEmpty());
        assertEquals("Volcano", entrenador.getUbicacion().getNombre() );
    }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnDojoSeActualizanSusDatos()
    {
        //Setup(Given)
        Ubicacion dojoBD;
        Ubicacion ubicacionViejaBD;
        Entrenador entrenador;
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Dojo Desert");
        dojoBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("Dojo Desert");});
        ubicacionViejaBD= Runner.runInSession(() -> { return ubicacionDAO.recuperar("El Origen 2");});
        entrenador      = Runner.runInSession(() -> { return entrenadorDAO.recuperar("Pepe DePrueba");});

        //Test(Then)
        assertFalse(dojoBD.getEntrenadores().isEmpty());
        assertEquals(2, dojoBD.getEntrenadores().size());
        assertTrue(ubicacionViejaBD.getEntrenadores().isEmpty());
        assertEquals("Dojo Desert", entrenador.getUbicacion().getNombre() );
    }

    @Test(expected = RuntimeException.class)
    public void siElMapaServiceIntentaMoverAUnEntrenadorAUnaUbicacionQueNoExisteTiraUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcanos");

        //Test(Then)
    }

    @Test(expected = RuntimeException.class)
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
        catch (RuntimeException e)
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
        catch (RuntimeException e)
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
}