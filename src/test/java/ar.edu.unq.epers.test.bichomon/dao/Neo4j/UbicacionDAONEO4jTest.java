package ar.edu.unq.epers.test.bichomon.dao.Neo4j;

import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionMuyLejanaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import extra.BootstrapNeo4J;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UbicacionDAONEO4jTest {

    private BootstrapNeo4J bootstrapNeo4j;
    private Guarderia guarderiaSut;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private Dojo dojoConCampeon;
    private Pueblo pueblo;
    @Before
    public void setUp() throws Exception
    {
        ubicacionDAONEO4J = new UbicacionDAONEO4J();

        dojoConCampeon = new Dojo();
        dojoConCampeon.setNombre("Dojo");

        pueblo = new Pueblo();
        pueblo.setNombre("Pueblo");


        guarderiaSut   = new Guarderia();
        guarderiaSut.setId(10);
        guarderiaSut.setNombre("Guarderia No te quiere nadie");

        this.bootstrapNeo4j         = new BootstrapNeo4J();
        this.bootstrapNeo4j.crearDatos();
    }

    @After
    public void tearDown() throws Exception {   this.bootstrapNeo4j.limpiarTabla(); }

    @Test
    public void CrearUbicacion()
    {
        ubicacionDAONEO4J.create(guarderiaSut);
        ubicacionDAONEO4J.create(dojoConCampeon);
        ubicacionDAONEO4J.create(pueblo);
        assertTrue(ubicacionDAONEO4J.existeUbicacion(guarderiaSut.getNombre()));
        assertTrue(ubicacionDAONEO4J.existeUbicacion(dojoConCampeon.getNombre()));
        assertTrue(ubicacionDAONEO4J.existeUbicacion(pueblo.getNombre()));
        assertFalse(ubicacionDAONEO4J.existeUbicacion("Inimputable"));
    }

    @Test
    public void ConectarDosCaminos(){

        ubicacionDAONEO4J.create(guarderiaSut);
        ubicacionDAONEO4J.create(dojoConCampeon);
        ubicacionDAONEO4J.conectar(guarderiaSut.getNombre(),dojoConCampeon.getNombre(),TipoCamino.AEREO);
        assertTrue(ubicacionDAONEO4J.estanConectados(guarderiaSut.getNombre(),dojoConCampeon.getNombre()));

    }

    @Test
    public void SiLePidoElCaminoADojoOrigenDesdePuebloOrigenMeDevuelveElCaminoMasBarato()
    {
        //Setup (Given)
        Camino camino;
        //Exercise (When)
        camino  = this.ubicacionDAONEO4J.caminoA("Pueblo Origen", "Dojo Origen");
        //Test (Then)
        assertEquals("Pueblo Origen", camino.getDesdeUbicacion());
        assertEquals(TipoCamino.TERRESTRE, camino.getTipo());
        assertEquals(1, camino.getCosto());
        assertEquals("Dojo Origen", camino.getHastaUbicacion());
    }

    @Test(expected = UbicacionMuyLejanaException.class)
    public void SiLePidoElCaminoADojoLavandaDesdePuebloOrigenMeDevuelveUnaExcepcion()
    {
        //Setup (Given)
        //Exercise (When)
        this.ubicacionDAONEO4J.caminoA("Pueblo Origen", "Dojo Lavanda");
        //Test (Then)
    }

    @Test
    public void SiLePidoElCaminoMasCortoADojoLavandaDesdePuebloOrigenMeDevuelveUnaListaDeDosCaminos()
    {
        //Setup (Given)
        List<Camino> caminos;
        //Exercise (When)
        caminos = this.ubicacionDAONEO4J.caminoMasCortoA("Pueblo Origen", "Dojo Lavanda");
        //Test (Then)
        assertEquals(2, caminos.size());
        assertEquals("Pueblo Origen", caminos.get(0).getDesdeUbicacion());
        assertEquals(TipoCamino.TERRESTRE, caminos.get(0).getTipo());
        assertEquals(1, caminos.get(0).getCosto());
        assertEquals("Pueblo Lavanda", caminos.get(0).getHastaUbicacion());
        assertEquals("Pueblo Lavanda", caminos.get(1).getDesdeUbicacion());
        assertTrue(caminos.get(1).getTipo() == TipoCamino.TERRESTRE || caminos.get(1).getTipo() == TipoCamino.AEREO);
        assertTrue(caminos.get(1).getCosto() == 1 || caminos.get(1).getCosto() == 5);
        assertEquals("Dojo Lavanda", caminos.get(1).getHastaUbicacion());
    }

    @Test(expected = UbicacionMuyLejanaException.class)
    public void SiLePidoElCaminoMasCortoADojoLavandaDesdeLaGuarderiaMeDaUnaExcepcion()
    {
        //Setup (Given)
        //Exercise (When)
        this.ubicacionDAONEO4J.caminoMasCortoA("La Guarderia", "Dojo Lavanda");
        //Test (Then)
    }

    @Test
    public void AlPreguntarLasUbicacionesConectadasPorCaminosTerrestresAPuebloLavandaRespondePuebloOrigenYDojoLavanda()
    {
        //Setup (Given)
        List<String> ubicacionesConectadas;
        //Exercise (When)
        ubicacionesConectadas   = this.ubicacionDAONEO4J.conectados(TipoCamino.TERRESTRE.name(), "Pueblo Lavanda");
        //Test (Then)
        assertEquals(2, ubicacionesConectadas.size());
        assertTrue(ubicacionesConectadas.containsAll(Arrays.asList("Dojo Lavanda","Pueblo Origen")));
    }

    @Test
    public void AlPreguntarLasUbicacionesConectadasPorCaminosAereosADojoLavandaRespondeDojoOrigen()
    {
        //Setup (Given)
        List<String> ubicacionesConectadas;
        //Exercise (When)
        ubicacionesConectadas   = this.ubicacionDAONEO4J.conectados(TipoCamino.AEREO.name(), "Dojo Lavanda");
        //Test (Then)
        assertArrayEquals(new String[]{"Pueblo Lavanda"}, ubicacionesConectadas.toArray());
    }

    @Test
    public void AlPreguntarLasUbicacionesConectadasPorCaminosMaritimosAPuebloOrigenRespondeDojoLavanda()
    {
        //Setup (Given)
        List<String> ubicacionesConectadas;
        //Exercise (When)
        ubicacionesConectadas   = this.ubicacionDAONEO4J.conectados(TipoCamino.MARITIMO.name(), "Pueblo Origen");
        //Test (Then)
        assertArrayEquals(new String[]{"Dojo Lavanda"}, ubicacionesConectadas.toArray());
    }

    @Test
    public void AlPreguntarLasUbicacionesConectadasPorCaminosTerrestresALaGuarderiaRespondeUnaListaVacia()
    {
        //Setup (Given)
        List<String> ubicacionesConectadas;
        //Exercise (When)
        ubicacionesConectadas   = this.ubicacionDAONEO4J.conectados(TipoCamino.TERRESTRE.name(), "La Guarderia");
        //Test (Then)
        assertArrayEquals(new String[]{}, ubicacionesConectadas.toArray());
    }


}