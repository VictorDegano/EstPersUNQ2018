package ar.edu.unq.epers.test.bichomon.dao.Neo4j;

import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
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

import static org.junit.Assert.*;

public class UbicacionDAONEO4jTest {

    private BootstrapNeo4J bootstrapNeo4j;
    private Bicho nuevoBicho;
    private Guarderia guarderiaSut;
    private Entrenador entrenador;
    private Bicho bicho2;
    private Entrenador entrenador2;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private Dojo dojoConCampeon;
    private Pueblo pueblo;
    @Before
    public void setUp() throws Exception
    {
        ubicacionDAONEO4J = new UbicacionDAONEO4J();

        Especie rojomon     = new Especie();
        rojomon.setNombre("Rojomon");
        rojomon.setTipo(TipoBicho.FUEGO);
        rojomon.setAltura(180);
        rojomon.setPeso(75);
        rojomon.setEnergiaIncial(100);
        rojomon.setUrlFoto("/image/rojomon.jpg");

        Especie amarillo = new Especie();
        amarillo.setNombre("Amarillomon");
        amarillo.setTipo(TipoBicho.ELECTRICIDAD);
        amarillo.setAltura(170);
        amarillo.setPeso(69);
        amarillo.setEnergiaIncial(300);
        amarillo.setUrlFoto("/image/amarillomon.png");

        dojoConCampeon = new Dojo();
        dojoConCampeon.setNombre("Dojo");

        pueblo = new Pueblo();
        pueblo.setNombre("Pueblo");

        nuevoBicho          = new Bicho(rojomon, "");
        bicho2 = new Bicho(amarillo,"");
        guarderiaSut   = new Guarderia();
        entrenador = new Entrenador();
        entrenador.getBichosCapturados().add(nuevoBicho);
        entrenador.setNombre("Carlos");
        entrenador2 = new Entrenador();
        entrenador2.getBichosCapturados().add(bicho2);
        entrenador2.setNombre("Pepe");
        nuevoBicho.setDuenio(entrenador);
        bicho2.setDuenio(entrenador2);
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

        ubicacionDAONEO4J.conectar(guarderiaSut.getNombre(),dojoConCampeon.getNombre(),"aereo");
    }

    @Test
    public void SiLePidoElCaminoADojoOrigenDesdePuebloOrigenMeDevuelveElCaminoMasBaratocaminoA()
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

}