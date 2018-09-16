package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import extra.Bootstrap;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

public class EntrenadorDAOHibernateTest {

    private EntrenadorDAOHibernate entrenadorDAOSut;
    private Bootstrap bootstraper;

    @Before
    public void setUp() throws Exception {
        bootstraper = new Bootstrap();
        entrenadorDAOSut = new EntrenadorDAOHibernate();
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
        assertEquals(1, entrenadorRecuperado.getNivel());
        assertEquals("El Origen", entrenadorRecuperado.getUbicacion().getNombre());
    }

    @Test(expected = PersistenceException.class)
    public void siGuardoUnEntrenadorSinNombreDaUnaExcepcion() {
        //Setup(Given)
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setExperiencia(0);
        entrenadorAGuardar.setNivel(1);

        //Exercise(When)
        Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return null;
        });
        //Test(Then)
    }

    @Test
    public void siGuardoUnEntrenadorQueNoEstaEnLaBaseDeDatosSeGuardaCorrectamente() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setNombre("Juan Rebenque");
        entrenadorAGuardar.setExperiencia(10);
        entrenadorAGuardar.setNivel(1);

        //Exercise(When)
        entrenadorRecuperado = Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return entrenadorDAOSut.recuperar("Juan Rebenque");
        });
        //Test(Then)
        assertEquals("Juan Rebenque", entrenadorRecuperado.getNombre());
        assertEquals(10, entrenadorRecuperado.getExperiencia());
        assertEquals(1, entrenadorRecuperado.getNivel());
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaGuardarUnEntrenadorConUnNombreQueYaExisteDaExcepcion() {
        //Setup(Given)
        Entrenador entrenadorAGuardar;
        entrenadorAGuardar = new Entrenador();
        entrenadorAGuardar.setNombre("Pepe Pepon");
        entrenadorAGuardar.setExperiencia(0);
        entrenadorAGuardar.setNivel(1);

        //Exercise(When)
        Runner.runInSession(() -> {
            entrenadorDAOSut.guardar(entrenadorAGuardar);
            return null;
        });
        //Test(Then)
    }

    @Test
    public void siSeActualizarUnEntrenadorSinCambiarSuNombreSeActualizaCorrectamente() {
        //Setup(Given)
        Entrenador entrenadorRecuperado;
        Entrenador entrenadorAModificar = Runner.runInSession(() -> { return entrenadorDAOSut.recuperar("Pepe Pepon"); });
        entrenadorAModificar.setExperiencia(101);
        entrenadorAModificar.setNivel(2);

        //Exercise(When)
        entrenadorRecuperado    = Runner.runInSession(() -> {entrenadorDAOSut.actualizar(entrenadorAModificar);
                                                             return entrenadorDAOSut.recuperar("Pepe Pepon");});

        //Test(Then)
        assertEquals("Pepe Pepon", entrenadorRecuperado.getNombre());
        assertEquals(101, entrenadorRecuperado.getExperiencia());
        assertEquals(2, entrenadorRecuperado.getNivel());
    }

    @Test(expected = PersistenceException.class)
    public void siSeActualizarUnEntrenadorConUnNombreQueNoExisteNoLoAgrega() {
        //Setup(Given)
        Entrenador entrenadorAActualizar= new Entrenador();
        entrenadorAActualizar.setNombre("Pepe");
        entrenadorAActualizar.setExperiencia(12);
        entrenadorAActualizar.setNivel(1);

        //Exercise(When)
        Runner.runInSession(() -> {entrenadorDAOSut.actualizar(entrenadorAActualizar);
                                   return entrenadorDAOSut.recuperar("Pepe"); });
        //Test(Then)
    }
}