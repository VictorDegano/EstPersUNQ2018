package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionCampeonException;
import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UbicacionTest {

    private Ubicacion ubicacionSUT1;
    private Ubicacion ubicacionSUT2;
    private Ubicacion ubicacionSUT3;
    private Ubicacion ubicacionSUT4;
    private Bicho nuevoBicho;

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
        Campeon nuevoCampeon= new Campeon();
        nuevoCampeon.setBichoCampeon(nuevoBicho);
        dojoConCampeon.setCampeonActual(nuevoCampeon);

        ubicacionSUT1       = new Pueblo();
        ubicacionSUT1.setNombre("La Prueba");

        ubicacionSUT2       = new Guarderia();
        ubicacionSUT2.setNombre("La Guarderia de Prueba");

        ubicacionSUT3       = new Dojo();
        ubicacionSUT3.setNombre("Dojo Prueba");

        ubicacionSUT4       = dojoConCampeon;
        ubicacionSUT4.setNombre("Dojo Campeonus");
    }

    @Test(expected = UbicacionCampeonException.class)
    public void SiLePidoElcampeonActualAUnaGuarderiaOPuebloMeDevuelveNull()
    {
        //Setup(Given)
        Bicho campeonPueblo;
        Bicho campeonGuarderia;

        //Exercise(When)
        campeonGuarderia= ubicacionSUT2.campeonActual();
        campeonPueblo   = ubicacionSUT1.campeonActual();

        //Test(Then)
        fail("En esta ubicacion no existen los campeones.");
    }

    @Test
    public void SiLePidoElcampeonActualAUnDojoSinCampeonMeDevuelveNull()
    {
        //Setup(Given)
        Bicho campeonDojo;
        //Exercise(When)
        campeonDojo= ubicacionSUT3.campeonActual();

        //Test(Then)
        assertNull(campeonDojo);
    }

    @Test
    public void SiLePidoElcampeonActualAUnDojoConCampeonMeDeVuelveDevuelveElBichoCampeon()
    {
        //Setup(Given)
        Bicho campeonDojo;

        //Exercise(When)
        campeonDojo= ubicacionSUT4.campeonActual();

        //Test(Then)
        assertNotNull(campeonDojo);
    }

    @Test(expected = UbicacionIncorrectaException.class)
    public void SiSeAbandonaUnBichomonEnUnDojoSeDaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        ubicacionSUT4.refugiar(nuevoBicho);

        //Test(Then)
    }

    @Test(expected = UbicacionIncorrectaException.class)
    public void SiSeAbandonaUnBichomonEnUnPuebloSeDaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        ubicacionSUT1.refugiar(nuevoBicho);

        //Test(Then)
    }

    @Test
    public void SiSebuscaUnBichomonEnUnDojoDondeNoHayCampeonSeDevuelveUnNull()
    {
        //Setup(Given)
        Entrenador unEntrenador = new Entrenador();
        unEntrenador.setExperiencia(10000);
        unEntrenador.setNivel(new Nivel(10, 7000, 7999, 13));

        //Exercise(When)
        Bicho bichoEncontrado   = ubicacionSUT3.buscar(unEntrenador);

        //Test(Then)
        assertNull(bichoEncontrado);
    }

}