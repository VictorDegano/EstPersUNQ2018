package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
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
        Dojo dojoConCampeon = new Dojo();
        nuevoBicho          = new Bicho(rojomon, "");
        guarderiaSut   = new Guarderia();
        guarderiaSut.setNombre("Guarderia NoTeQuiereNadie");
    }

    @Test
    public void SiSeAbandonaUnBichomonEnUnaGuarderiaEstaLoTieneEnAdopcion()
    {
        //Setup(Given)
        //Exercise(When)
        guarderiaSut.abandonar(nuevoBicho);
        //Test(Then)
        assertFalse(guarderiaSut.getBichosAbandonados().isEmpty());
        assertTrue(guarderiaSut.getBichosAbandonados().contains(nuevoBicho));
    }
}