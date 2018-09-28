package ar.edu.unq.epers.test.bichomon.model;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DuelosTest {

    private Entrenador entrenadorSUT;
    private Ubicacion unaUbicacion;
    private Dojo unaUbicacion2;
    private Guarderia unaGuarderia;
    private @Spy Bicho nuevoBicho;
    private @Spy Bicho bichoDenadie;
    private Nivel nivel1;
    private Nivel nivel2;
    private Nivel nivel3;
    private Campeon campeonto;


    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
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
        nuevoBicho      = spy(new Bicho(rojomon, ""));
        nuevoBicho.setEnergia(23456);
        nuevoBicho.setId(2);
        bichoDenadie    = spy(new Bicho(rojomon, ""));
        bichoDenadie.setEnergia(14);
        bichoDenadie.setId(6);

        unaUbicacion    = new Pueblo();
        unaUbicacion.setNombre("El Origen");

        unaUbicacion2   = new Dojo();
        unaUbicacion2.setNombre("Dojo el Origen");


        unaGuarderia = new Guarderia();
        unaGuarderia.setNombre("Guarderia el Origen");

        entrenadorSUT.setUbicacion(unaUbicacion);
        entrenadorSUT.getBichosCapturados().add(nuevoBicho);

        campeonto   = new Campeon();
        campeonto.setBichoCampeon(bichoDenadie);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void unEntrenadorQuierePelearEnUnaGuarderiaYNoPuede()
    {
        //Setup(Given)
        entrenadorSUT.moverse(unaGuarderia);
        String mensajePueblo="";

        try
        {   entrenadorSUT.retar(nuevoBicho);   }
        catch (UbicacionIncorrectaException e)
        {   mensajePueblo = e.getMessage(); }


        //Test(Then)
        assertEquals("La Ubicacion: Guarderia el Origen es incorrecta. No se puede pelear en esta Ubicacion", mensajePueblo);
    }



    @Test
    public void unEntrenadorRetaAUnDojoYSuBichoGana()
    {
        when(bichoDenadie.daño()).thenReturn(1);
        when(nuevoBicho.daño()).thenReturn(2);

        unaUbicacion2.setCampeonActual(campeonto);
        entrenadorSUT.moverse(unaUbicacion2);
        entrenadorSUT.retar(nuevoBicho);
        Registro ganador=unaUbicacion2.getHistorial().get(0);

        assertEquals(nuevoBicho , ganador.ganador);


    }

    @Test
    public void unEntrenadorRetaAUnDojoYSuBichoPierdePorTurnos()
    {
        when(bichoDenadie.daño()).thenReturn(3);
        when(nuevoBicho.daño()).thenReturn(1);

        unaUbicacion2.setCampeonActual(campeonto);
        entrenadorSUT.moverse(unaUbicacion2);
        entrenadorSUT.retar(nuevoBicho);
        Registro ganador=unaUbicacion2.getHistorial().get(0);

        assertEquals(bichoDenadie , ganador.ganador);


    }

    @Test
    public void unEntrenadorRetaAUnDojoVacioYSeCoronaGanador()
    {
        when(bichoDenadie.daño()).thenReturn(1);
        when(nuevoBicho.daño()).thenReturn(2);
        entrenadorSUT.moverse(unaUbicacion2);
        entrenadorSUT.retar(nuevoBicho);
        Registro ganador=unaUbicacion2.getHistorial().get(0);

        assertEquals(nuevoBicho , ganador.ganador);


    }
}
