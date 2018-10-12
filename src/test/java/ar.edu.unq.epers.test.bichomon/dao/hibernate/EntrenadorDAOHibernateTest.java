package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.TestService;
import extra.Bootstrap;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class EntrenadorDAOHibernateTest {

    private EntrenadorDAOHibernate entrenadorDAOSut;
    private Bootstrap bootstraper;
    private TestService testService;

    @Before
    public void setUp() throws Exception {
        testService     = new TestService();
        bootstraper     = new Bootstrap();
        entrenadorDAOSut= new EntrenadorDAOHibernate();
        Runner.runInSession(() -> { bootstraper.crearDatos();
                                    return null;});
    }

    @After
    public void tearDown() throws Exception {   bootstraper.limpiarTabla(); }

    @Test(expected = NoResultException.class)
    public void siSeRecuperaUnEntrenadorQueNoEstaEnLaBaseDeDatosDevuelveNull() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        //Exercise(When)
        entrenadorRecuperado = Runner.runInSession(() -> {
            return entrenadorDAOSut.recuperar("El Irrecuperable");
        });
        //Test(Then)
        fail("No hubo Excepcion");
    }

    @Test
    public void siSeRecuperaUnEntrenadorQueEstaEnLaBaseDeDatosEsteEsDevueltoCorrectamente() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        //Exercise(When)
        entrenadorRecuperado = Runner.runInSession(() -> {
            return entrenadorDAOSut.recuperar("Pepe Pepon");
        });
        //Test(Then)
        assertNotNull(entrenadorRecuperado);
        assertEquals("Pepe Pepon", entrenadorRecuperado.getNombre());
        assertEquals(0, entrenadorRecuperado.getExperiencia());
        assertEquals(1, entrenadorRecuperado.getNivel().getNroDeNivel());
        assertEquals("El Origen", entrenadorRecuperado.getUbicacion().getNombre());
    }

    @Test(expected = PersistenceException.class)
    public void siGuardoUnEntrenadorSinNombreDaUnaExcepcion() {
        //Setup(Given)
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setExperiencia(0);

        //Exercise(When)
        Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return null;
        });
        //Test(Then)
        fail("No hubo Excepcion");
    }

    @Test
    public void siGuardoUnEntrenadorQueNoEstaEnLaBaseDeDatosSeGuardaCorrectamente() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setNombre("Juan Rebenque");
        entrenadorAGuardar.setExperiencia(10);

        //Exercise(When)
        entrenadorRecuperado = Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return entrenadorDAOSut.recuperar("Juan Rebenque");
        });
        //Test(Then)
        assertEquals("Juan Rebenque", entrenadorRecuperado.getNombre());
        assertEquals(10, entrenadorRecuperado.getExperiencia());
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaGuardarUnEntrenadorConUnNombreQueYaExisteDaExcepcion() {
        //Setup(Given)
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setNombre("Pepe Pepon");
        entrenadorAGuardar.setExperiencia(0);

        //Exercise(When)
        Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return null;
        });
        //Test(Then)
        fail("No hubo Excepcion");
    }

    @Test
    public void siSeActualizarUnEntrenadorSinCambiarSuNombreSeActualizaCorrectamente() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        Entrenador entrenadorAModificar = Runner.runInSession(() -> { return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        entrenadorAModificar.setExperiencia(98);

        //Exercise(When)
        entrenadorRecuperado    = Runner.runInSession(() -> {entrenadorDAOSut.actualizar(entrenadorAModificar);
                                                             return entrenadorDAOSut.recuperar("Pepe Pepon");});

        //Test(Then)
        assertEquals("Pepe Pepon", entrenadorRecuperado.getNombre());
        assertEquals(98, entrenadorRecuperado.getExperiencia());
        assertEquals(1, entrenadorRecuperado.getNivel().getNroDeNivel());
    }

    @Test(expected = PersistenceException.class)
    public void siSeActualizarUnEntrenadorConUnNombreQueNoExisteNoLoAgrega() {
        //Setup(Given)
        Entrenador entrenadorAActualizar= new Entrenador();
        entrenadorAActualizar.setNombre("Pepe");
        entrenadorAActualizar.setExperiencia(12);

        //Exercise(When)
        Runner.runInSession(() -> {entrenadorDAOSut.actualizar(entrenadorAActualizar);
                                   return entrenadorDAOSut.recuperar("Pepe"); });
        //Test(Then)
        fail("No Hubo Excepcion");
    }

    @Test
    public void siSePideLaListaDeLosCampeonesYSoloHayUnDojoConCampeonDevuelveUnaListaConUnEntrenador()
    {
        //Setup(given)
        List<Entrenador> listaDeCampeones;
        Campeon unNuevoCampeon  = new Campeon();
        Dojo unDojoConCampeon   = new Dojo();
        Entrenador pepePepon    = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        unNuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
        unNuevoCampeon.setBichoCampeon(pepePepon.getBichosCapturados().get(0));
        unDojoConCampeon.setNombre("Dojo Champion");
        unDojoConCampeon.setCampeonActual(unNuevoCampeon);
        Runner.runInSession(() -> { testService.crearEntidad(unNuevoCampeon);
                                    testService.crearEntidad(unDojoConCampeon);
                                    return null; });
        //Exercise(when)
        listaDeCampeones    = Runner.runInSession(() -> {   return entrenadorDAOSut.campeones();});
        //Test(Then)
        assertEquals(1, listaDeCampeones.size());
        assertEquals("Pepe Pepon", listaDeCampeones.get(0).getNombre());
    }

    @Test
    public void siSePideLaListaDeLosCampeonesYNoHayDojoConCampeonDevuelveUnaListaVacia()
    {
        //Setup(given)
        List<Entrenador> listaDeCampeones;
        //Exercise(when)
        listaDeCampeones    = Runner.runInSession(() -> {   return entrenadorDAOSut.campeones();});
        //Test(Then)
        assertTrue(listaDeCampeones.isEmpty());
    }

    @Test
    public void siSePideLaListaDeLosCampeonesYSoloHayDosDojosConCampeonDelMismoEntrenadorDevuelveUnaListaConUnEntrenador()
    {
        //Setup(given)
        List<Entrenador> listaDeCampeones;
        Campeon unNuevoCampeon  = new Campeon();
        Campeon unNuevoCampeon1 = new Campeon();
        Dojo unDojoConCampeon   = new Dojo();
        Dojo unDojoConCampeon1  = new Dojo();
        Entrenador pepePepon    = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        unNuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
        unNuevoCampeon.setBichoCampeon(pepePepon.getBichosCapturados().get(0));
        unDojoConCampeon.setNombre("Dojo Champion");
        unDojoConCampeon.setCampeonActual(unNuevoCampeon);
        unNuevoCampeon1.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now().minusDays(20)));
        unNuevoCampeon1.setBichoCampeon(pepePepon.getBichosCapturados().get(1));
        unDojoConCampeon1.setNombre("Dojo Champion Fiesta");
        unDojoConCampeon1.setCampeonActual(unNuevoCampeon1);
        Runner.runInSession(() -> { testService.crearEntidad(unNuevoCampeon);
                                    testService.crearEntidad(unNuevoCampeon1);
                                    testService.crearEntidad(unDojoConCampeon);
                                    testService.crearEntidad(unDojoConCampeon1);
                                    return null; });
        //Exercise(when)
        listaDeCampeones    = Runner.runInSession(() -> {   return entrenadorDAOSut.campeones();});
        //Test(Then)
        assertEquals(1, listaDeCampeones.size());
        assertEquals("Pepe Pepon", listaDeCampeones.get(0).getNombre());
    }

    @Test
    public void siSePideLaListaDeLosCampeonesYSoloHayDosDojosConCampeonDevuelveUnaListaConDosEntrenadoresOrdenadosDesdeElCampeonMasAntiguoAlMasNuevo()
    {
        //Setup(given)
        List<Entrenador> listaDeCampeones;
        Campeon unNuevoCampeon  = new Campeon();
        Campeon unNuevoCampeon1 = new Campeon();
        Dojo unDojoConCampeon   = new Dojo();
        Dojo unDojoConCampeon1  = new Dojo();
        Entrenador pepePepon    = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        Entrenador elLoquillo    = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("El Loquillo"); });
        unNuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,4,20,12,50)));
        unNuevoCampeon.setBichoCampeon(pepePepon.getBichosCapturados().get(0));
        unDojoConCampeon.setNombre("Dojo Champion");
        unDojoConCampeon.setCampeonActual(unNuevoCampeon);
        unNuevoCampeon1.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,1,20,12,50)));
        unNuevoCampeon1.setBichoCampeon(elLoquillo.getBichosCapturados().get(0));
        unDojoConCampeon1.setNombre("Dojo Champion Fiesta");
        unDojoConCampeon1.setCampeonActual(unNuevoCampeon1);
        Runner.runInSession(() -> { testService.crearEntidad(unNuevoCampeon);
                                    testService.crearEntidad(unNuevoCampeon1);
                                    testService.crearEntidad(unDojoConCampeon);
                                    testService.crearEntidad(unDojoConCampeon1);
                                    return null; });
        //Exercise(when)
        listaDeCampeones    = Runner.runInSession(() -> {   return entrenadorDAOSut.campeones();});
        //Test(Then)
        assertEquals(2, listaDeCampeones.size());
        assertEquals("El Loquillo", listaDeCampeones.get(0).getNombre());
        assertEquals("Pepe Pepon", listaDeCampeones.get(1).getNombre());
    }

    @Test
    public void siSePideLaListaDeLosCampeonesYSoloHayTresDojosConCampeonYDosDelMismoEntrenadorDevuelveUnaListaConDosEntrenadoresOrdenadosDesdeElCampeonMasAntiguoAlMasNuevo()
    {
        //Setup(given)
        List<Entrenador> listaDeCampeones;
        Campeon unNuevoCampeon  = new Campeon();
        Campeon unNuevoCampeon1 = new Campeon();
        Campeon unNuevoCampeon2 = new Campeon();
        Dojo unDojoConCampeon   = new Dojo();
        Dojo unDojoConCampeon1  = new Dojo();
        Dojo unDojoConCampeon2  = new Dojo();
        Entrenador pepePepon    = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        Entrenador elLoquillo   = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("El Loquillo"); });
        Entrenador ortigoza      = Runner.runInSession(() -> {   return entrenadorDAOSut.recuperar("Ortigoza"); });
        unNuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.of(2018,4,20,12,50)));
        unNuevoCampeon.setBichoCampeon(pepePepon.getBichosCapturados().get(0));
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
        Runner.runInSession(() -> { testService.crearEntidad(unNuevoCampeon);
                                    testService.crearEntidad(unNuevoCampeon1);
                                    testService.crearEntidad(unDojoConCampeon);
                                    testService.crearEntidad(unDojoConCampeon1);
                                    return null; });
        Runner.runInSession(() -> { testService.crearEntidad(unNuevoCampeon2);
                                    testService.crearEntidad(unDojoConCampeon2);
                                    return null; });
        //Exercise(when)
        listaDeCampeones    = Runner.runInSession(() -> {   return entrenadorDAOSut.campeones();});
        //Test(Then)
        assertEquals(2, listaDeCampeones.size());
        assertEquals("Pepe Pepon", listaDeCampeones.get(0).getNombre());
        assertEquals("El Loquillo", listaDeCampeones.get(1).getNombre());
    }


}