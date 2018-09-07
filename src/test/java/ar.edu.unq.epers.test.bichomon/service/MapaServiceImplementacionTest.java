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

        pepePrueba.setNombre("Pepe DePrueba");
        pepePrueba.setUbicacion(unaUbicacion);
        unaUbicacion.agregarEntrenador(pepePrueba);

        entrenadorDAO.guardar(pepePrueba);
        ubicacionDAO.guardar(unaUbicacion);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void siElMapaServiceMueveUnEntrenadorAUnaNuevaUbicacionLaViejaUbicacion()
    {
        //Setup(Given)
        Ubicacion unaUbicacionNueva = new Ubicacion();
        unaUbicacionNueva.setNombre("Volcano");
        ubicacionDAO.guardar(unaUbicacionNueva);

        //Exercise(When)
        mapaServiceSUT.mover("Pepe DePrueba", "Volcano");

        //Test(Then)
        assertFalse(unaUbicacionNueva.getEntrenadores().isEmpty());
        assertEquals(1, unaUbicacionNueva.getEntrenadores().size());
        assertTrue(unaUbicacion.getEntrenadores().isEmpty());
        assertEquals("Volcano", pepePrueba.getUbicacion().getNombre() );
    }

    @Test
    public void cantidadEntrenadores() {    }

    @Test
    public void campeon() { }

    @Test
    public void campeonHistorico() {    }
}