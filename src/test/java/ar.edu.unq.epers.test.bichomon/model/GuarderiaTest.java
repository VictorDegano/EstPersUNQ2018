package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuarderiaTest {

    private Bicho nuevoBicho;
    private Guarderia guarderiaSut;
    private Entrenador entrenador;
    private Bicho bicho2;
    private Entrenador entrenador2;
    @Before
    public void setUp() throws Exception
    {
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

        Dojo dojoConCampeon = new Dojo();
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
        guarderiaSut.setNombre("Guarderia NoTeQuiereNadie");


    }

    @Test
    public void SiSeAbandonaUnBichomonEnUnaGuarderiaEstaLoTieneEnAdopcion()
    {
        //Setup(Given)
        //Exercise(When)
        guarderiaSut.refugiar(nuevoBicho);
        //Test(Then)
        assertFalse(guarderiaSut.getBichosAbandonados().isEmpty());
        assertTrue(guarderiaSut.getBichosAbandonados().contains(nuevoBicho));
    }

    @Test
    public void seRealizaUnaBusquedaEnLaGuarderia(){
        guarderiaSut.refugiar(nuevoBicho);
        guarderiaSut.refugiar(bicho2);
        Bicho bicho = guarderiaSut.buscarBicho(entrenador2);
        assertEquals(bicho.getEspecie().getNombre(),"Rojomon");
        assertEquals(guarderiaSut.getBichosAbandonados().size(),1);
    }
}