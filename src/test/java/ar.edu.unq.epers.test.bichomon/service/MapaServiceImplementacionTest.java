package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.mock.EntrenadorDAOMock;
import ar.edu.unq.epers.bichomon.backend.mock.UbicacionDAOMock;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Limpiador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapaServiceImplementacionTest
{
    private MapaServiceImplementacion mapaServiceSUT;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private Ubicacion unaUbicacion;
    private Ubicacion unaUbicacionNueva;
    private Entrenador pepePrueba;

    @Before
    public void setUp() throws Exception
    {
        entrenadorDAO   = new EntrenadorDAOHibernate();
        ubicacionDAO    = new UbicacionDAOHibernate();
        mapaServiceSUT  = new MapaServiceImplementacion(entrenadorDAO, ubicacionDAO);
        pepePrueba      = new Entrenador();
        unaUbicacion    = new Ubicacion();
        unaUbicacion.setNombre("El Origen 2");
        unaUbicacionNueva = new Ubicacion();
        unaUbicacionNueva.setNombre("Volcano");

        pepePrueba.setNombre("Pepe DePrueba");
        pepePrueba.setUbicacion(unaUbicacion);
        unaUbicacion.agregarEntrenador(pepePrueba);

        Runner.runInSession(()-> {  entrenadorDAO.guardar(pepePrueba);
                                    ubicacionDAO.guardar(unaUbicacion);
                                    ubicacionDAO.guardar(unaUbicacionNueva);
                                    return null; });
    }

    @After
    public void tearDown() throws Exception
    {   Limpiador.getInstance().limpiarTabla(); }

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
    public void campeon() { }

    @Test
    public void campeonHistorico() {    }
}