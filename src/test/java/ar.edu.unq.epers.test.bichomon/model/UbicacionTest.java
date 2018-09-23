package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UbicacionTest {

    private Ubicacion ubicacionSUT1;
    private Ubicacion ubicacionSUT2;
    private Ubicacion ubicacionSUT3;
    private Ubicacion ubicacionSUT4;

    @Before
    public void setUp() throws Exception
    {
        Especie rojomon = new Especie();
        rojomon.setNombre("Rojomon");
        rojomon.setTipo(TipoBicho.FUEGO);
        rojomon.setAltura(180);
        rojomon.setPeso(75);
        rojomon.setEnergiaIncial(100);
        rojomon.setUrlFoto("/image/rojomon.jpg");
        Dojo dojoConCampeon = new Dojo();
        Bicho nuevoBichoCampeon  = new Bicho(rojomon, "");
        Campeon nuevoCampeon= new Campeon();
        nuevoCampeon.setBichoCampeon(nuevoBichoCampeon);
        dojoConCampeon.setCampeonActual(nuevoCampeon);

        ubicacionSUT1   = new Pueblo();
        ubicacionSUT1.setNombre("La Prueba");

        ubicacionSUT2   = new Guarderia();
        ubicacionSUT2.setNombre("La Guarderia de Prueba");

        ubicacionSUT3   = new Dojo();
        ubicacionSUT3.setNombre("Dojo Prueba");

        ubicacionSUT4   = dojoConCampeon;
    }

    @Test
    public void SiLePidoElcampeonActualAUnaGuarderiaOPuebloMeDevuelveNull()
    {
        //Setup(Given)
        Bicho campeonPueblo;
        Bicho campeonGuarderia;

        //Exercise(When)
        campeonGuarderia= ubicacionSUT2.campeonActual();
        campeonPueblo   = ubicacionSUT1.campeonActual();

        //Test(Then)
        assertNull(campeonGuarderia);
        assertNull(campeonPueblo);
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

}