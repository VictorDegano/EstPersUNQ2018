package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.mock.EntrenadorDAOMock;
import ar.edu.unq.epers.bichomon.backend.mock.UbicacionDAOMock;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.mapa.MapaServiceImplementacion;
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
        entrenadorDAO   = new EntrenadorDAOMock();
        ubicacionDAO    = new UbicacionDAOMock();
        mapaServiceSUT  = new MapaServiceImplementacion(entrenadorDAO, ubicacionDAO);
        pepePrueba      = new Entrenador();
        unaUbicacion    = new Ubicacion();
        unaUbicacion.setNombre("El Origen 2");
        unaUbicacionNueva = new Ubicacion();
        unaUbicacionNueva.setNombre("Volcano");

        pepePrueba.setNombre("Pepe DePrueba");
        pepePrueba.setUbicacion(unaUbicacion);
        unaUbicacion.agregarEntrenador(pepePrueba);

        entrenadorDAO.guardar(pepePrueba);
        ubicacionDAO.guardar(unaUbicacion);
        ubicacionDAO.guardar(unaUbicacionNueva);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnaNuevaUbicacionSeActualizanSusDatos()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcano");

        //Test(Then)
        assertFalse(unaUbicacionNueva.getEntrenadores().isEmpty());
        assertEquals(1, unaUbicacionNueva.getEntrenadores().size());
        assertTrue(unaUbicacion.getEntrenadores().isEmpty());
        assertEquals("Volcano", pepePrueba.getUbicacion().getNombre() );
    }

    @Test(expected = RuntimeException.class)
    public void siElMapaServiceIntentaMoverAUnEntrenadorAUnaUbicacionQueNoExisteTiraUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcanos");

        //Test(Then)
        assertTrue(unaUbicacion.getEntrenadores().isEmpty());
        assertEquals("Volcanos", pepePrueba.getUbicacion().getNombre() );
    }

    @Test(expected = RuntimeException.class)
    public void siElMapaServiceIntentaMoverAUnEntrenadorQueNoExisteTiraUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        mapaServiceSUT.mover("Pepe", "Volcano");

        //Test(Then)
        assertTrue(unaUbicacion.getEntrenadores().isEmpty());
        assertEquals("Volcano", pepePrueba.getUbicacion().getNombre() );
    }

    @Test
    public void siOcurreUnaExcepcionAlMoverElEntrenadorYLasUbicacionesNoSonAfectadas()
    {
        //Setup(Given)
        String mensajeDeError    = "";
        //Exercise(When)
        try
        {   mapaServiceSUT.mover("Pepe", "Volcanos");    }
        catch (RuntimeException e)
        {   mensajeDeError  = e.getMessage();   }

        //Test(Then)
        assertFalse(unaUbicacion.getEntrenadores().isEmpty());
        assertEquals("El Origen 2", pepePrueba.getUbicacion().getNombre() );
        assertEquals("Nombre de entrenador: Pepe o nombre de ubicacion: Volcanos incorrectos", mensajeDeError);
    }

    @Test
    public void cantidadEntrenadores() {    }

    @Test
    public void campeon() { }

    @Test
    public void campeonHistorico() {    }
}