package ar.edu.unq.epers.bichomon.backend.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.TestService;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EspecieDAOHibernateTest {

    private TestService unTestService;
    private EspecieDAO especieDAOSUT;
    private Bootstrap bootstrap;

    @Before
    public void setUp() throws Exception
    {
        this.especieDAOSUT  = new EspecieDAOHibernate();
        this.unTestService  = new TestService();
        this.bootstrap      = new Bootstrap();
        Runner.runInSession(()-> {  bootstrap.crearDatos();
                                    return null;});
    }

    @After
    public void tearDown() throws Exception
    {
        Runner.runInSession(()-> {  bootstrap.limpiarTabla();
                                    return null;});
    }

    @Test
    public void siSePideLaEspecieLiderYHayDojosConCampeonesEstaEsDevuelta()
    {
        //Setup(given)
        Especie especieLider;
        Especie especieFort     = Runner.runInSession(() -> {   return especieDAOSUT.recuperar("Fortmon"); });
        Campeon unNuevoCampeon  = new Campeon();
        Campeon unNuevoCampeon1 = new Campeon();
        Campeon unNuevoCampeon2 = new Campeon();
        Dojo unDojoConCampeon   = new Dojo();
        Dojo unDojoConCampeon1  = new Dojo();
        Dojo unDojoConCampeon2  = new Dojo();
        Entrenador pepePepon    = Runner.runInSession(() -> {   return unTestService.recuperarEntidad(Entrenador.class,1); });
        Entrenador elLoquillo   = Runner.runInSession(() -> {   return unTestService.recuperarEntidad(Entrenador.class,2); });
        Bicho unBichoFort       = new Bicho(especieFort, "");

        unNuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,4,20,12,50)));
        unNuevoCampeon.setBichoCampeon(unBichoFort);
        unDojoConCampeon.setNombre("Dojo Champion");
        unDojoConCampeon.setCampeonActual(unNuevoCampeon);

        unNuevoCampeon1.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,5,20,12,50)));
        unNuevoCampeon1.setBichoCampeon(elLoquillo.getBichosCapturados().get(0));
        unDojoConCampeon1.setNombre("Dojo Champion Fiesta");
        unDojoConCampeon1.setCampeonActual(unNuevoCampeon1);

        unNuevoCampeon2.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,6,20,12,50)));
        unNuevoCampeon2.setBichoCampeon(pepePepon.getBichosCapturados().get(0));
        unDojoConCampeon2.setNombre("Dojo Champion Super Fieta");
        unDojoConCampeon2.setCampeonActual(unNuevoCampeon2);
        Runner.runInSession(() -> { unTestService.crearEntidad(unBichoFort);
                                    unTestService.crearEntidad(unNuevoCampeon);
                                    unTestService.crearEntidad(unDojoConCampeon);
                                    return null; });
        Runner.runInSession(() -> {
                                    unTestService.crearEntidad(unNuevoCampeon1);
                                    unTestService.crearEntidad(unDojoConCampeon1);
                                    return null; });
        Runner.runInSession(() -> { unTestService.crearEntidad(unNuevoCampeon2);
                                    unTestService.crearEntidad(unDojoConCampeon2);
                                    return null; });
        //Exercise(When)
        especieLider    = Runner.runInSession(() -> {   return especieDAOSUT.especieLider();});
        //Test(Then)
        assertNotNull(especieLider);
        assertEquals("Fortmon", especieLider.getNombre());
    }
}