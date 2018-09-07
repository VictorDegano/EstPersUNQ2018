package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntrenadorTest {

    private Entrenador entrenadorSUT;
    private Ubicacion unaUbicacion;
    private Ubicacion unaUbicacion2;

    @Before
    public void setUp() throws Exception
    {
        entrenadorSUT   = new Entrenador();
        entrenadorSUT.setNombre("Sutter");

        unaUbicacion    = new Ubicacion();
        unaUbicacion.setNombre("El Origen");

        unaUbicacion2   = new Ubicacion();
        unaUbicacion2.setNombre("Dojo el Origen");

        entrenadorSUT.setUbicacion(unaUbicacion);
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
}