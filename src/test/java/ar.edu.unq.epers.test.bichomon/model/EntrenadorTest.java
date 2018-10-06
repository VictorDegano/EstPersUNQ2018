package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntrenadorTest {

    private Entrenador entrenadorSUT;
    private Ubicacion unaUbicacion;
    private Ubicacion unaUbicacion2;
    private Guarderia unaGuarderia;
    private Bicho nuevoBicho;
    private Bicho bichoDenadie;
    private Nivel nivel1;
    private Nivel nivel2;
    private Nivel nivel3;

    @Before
    public void setUp() throws Exception
    {
        nivel1  = new Nivel(1, 1, 99, 4);
        nivel2  = new Nivel(2, 100, 399, 5);
        nivel3  = new Nivel(3, 400, 999, 6);
        nivel1.setNivelSiguiente(nivel2);
        nivel2.setNivelSiguiente(nivel3);

        entrenadorSUT   = new Entrenador();
        entrenadorSUT.setNombre("Sutter");

        Especie rojomon = new Especie();
        rojomon.setNombre("Rojomon");
        rojomon.setTipo(TipoBicho.FUEGO);
        rojomon.setAltura(180);
        rojomon.setPeso(75);
        rojomon.setEnergiaIncial(100);
        rojomon.setUrlFoto("/image/rojomon.jpg");
        nuevoBicho      = new Bicho(rojomon, "");
        nuevoBicho.setDuenio(entrenadorSUT);
        bichoDenadie    = new Bicho(rojomon, "");
        bichoDenadie.setEnergia(23456);

        unaUbicacion    = new Pueblo();
        unaUbicacion.setNombre("El Origen");

        unaUbicacion2   = new Dojo();
        unaUbicacion2.setNombre("Dojo el Origen");

        unaGuarderia = new Guarderia();
        unaGuarderia.setNombre("Guarderia el Origen");

        entrenadorSUT.setUbicacion(unaUbicacion);
        entrenadorSUT.getBichosCapturados().add(nuevoBicho);
    }

        @After
        public void tearDown() throws Exception {   }

    @Test
    public void si_Un_Entrenador_Se_Mueve_De_Una_Ubicacion_A_Otra_Al_Preguntarle_En_Cual_Esta_Responde_La_Nueva_Ubicacion()
    {
        //Setup(Given)
        unaUbicacion.agregarEntrenador(entrenadorSUT);
        //Exercise(When)
        entrenadorSUT.moverse(unaUbicacion2);
        //Test(Then)
        assertEquals(unaUbicacion2, entrenadorSUT.getUbicacion());
        assertTrue(unaUbicacion.getEntrenadores().isEmpty());
        assertFalse(unaUbicacion2.getEntrenadores().isEmpty());
    }

    @Test
    public void siUnEntrenadorIntentaAbandonarUnBichomonEnUnPuebloODojoRecibeUnaExcepcion()
    {
        //Setup(Given)
        String mensajePueblo= "";
        String mensajeDojo  = "";

        //Exercise(When)
        try
        {   entrenadorSUT.abandonarBicho(nuevoBicho);   }
        catch (UbicacionIncorrectaException e)
        {   mensajePueblo = e.getMessage(); }
        entrenadorSUT.moverse(unaUbicacion2);
        try
        {   entrenadorSUT.abandonarBicho(nuevoBicho);   }
        catch (UbicacionIncorrectaException e)
        {   mensajeDojo = e.getMessage(); }
        //Test(Then)
        assertEquals("La Ubicacion: El Origen es incorrecta. No se puede refugiar un bichomon en esta Ubicacion", mensajePueblo);
        assertEquals("La Ubicacion: Dojo el Origen es incorrecta. No se puede refugiar un bichomon en esta Ubicacion", mensajeDojo);
        assertFalse(entrenadorSUT.getBichosCapturados().isEmpty());
        assertTrue(entrenadorSUT.getBichosCapturados().contains(nuevoBicho));
    }

    @Test
    public void siUnEntrenadorAbandonaAUnBichomonEnUnaGuarderiaYaNoLoPosee()
    {
        //Setup(Given)
        entrenadorSUT.moverse(unaGuarderia);
        //Exercise(When)
        entrenadorSUT.abandonarBicho(nuevoBicho);
        //Test(Then)
        assertTrue(entrenadorSUT.getBichosCapturados().isEmpty());
        assertFalse(unaGuarderia.getBichosAbandonados().isEmpty());
        assertTrue(unaGuarderia.getBichosAbandonados().contains(nuevoBicho));
        assertEquals(1,unaGuarderia.getRegistroDeBichosAbandonados().size());
    }

    @Test
    public void siUnEntrenadorIntentaAbandonarAUnBichomonQueNoPoseeNoPasaNada()
    {
        //Setup(Given)
        entrenadorSUT.moverse(unaGuarderia);

        //Exercise(When)
        entrenadorSUT.abandonarBicho(bichoDenadie);

        //Test(Then)
        assertFalse(unaGuarderia.getBichosAbandonados().contains(nuevoBicho));
        assertFalse(entrenadorSUT.getBichosCapturados().isEmpty());
        assertTrue(unaGuarderia.getBichosAbandonados().isEmpty());
    }

    @Test
    public void siUnEntrenadorDeNivelUnoSinXPGanaCuarentaPuntosDeXPQuedaEnNivelUno()
    {
        //Setup(given)
        entrenadorSUT.setNivel(nivel1);
        entrenadorSUT.setExperiencia(0);
        //Exercise(When)
        entrenadorSUT.subirExperiencia(40);

        //Test(Then)
        assertEquals(1, entrenadorSUT.getNivel().getNroDeNivel());
        assertEquals(40, entrenadorSUT.getExperiencia());
    }

    @Test
    public void siUnEntrenadorDeNivelUnoObtieneUnaExperienciaQueLeHaceSuperarLosNoventaYNuevePuntosSubeAlNivelDos()
    {
        //Setup(given)
        entrenadorSUT.setNivel(nivel1);
        entrenadorSUT.setExperiencia(40);

        //Exercise(When)
        entrenadorSUT.subirExperiencia(61);

        //Test(Then)
        assertEquals(2, entrenadorSUT.getNivel().getNroDeNivel());
        assertEquals(101, entrenadorSUT.getExperiencia());
    }

    @Test
    public void siUnEntrenadorDeNivelUnoSinXPGanaElEquivalenteACuatrocientosCincuentaPuntosDeXPTerminaEnElNivel3()
    {
        //Setup(given)
        entrenadorSUT.setNivel(nivel1);
        entrenadorSUT.setExperiencia(0);

        //Exercise(When)
        entrenadorSUT.subirExperiencia(100);
        entrenadorSUT.subirExperiencia(350);

        //Test(Then)
        assertEquals(3, entrenadorSUT.getNivel().getNroDeNivel());
        assertEquals(450, entrenadorSUT.getExperiencia());
    }

}