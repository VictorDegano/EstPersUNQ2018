package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import extra.Limpiador;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

public class UbicacionDAOHibernateTest {

    UbicacionDAOHibernate   ubicacionDAOSut;

    @Before
    public void setUp() throws Exception
    {
        SessionFactoryProvider.getInstance();
        ubicacionDAOSut     = new UbicacionDAOHibernate();

        Runner.runInSession(()-> {ubicacionDAOSut.crerDatosIniciales();
                                  return null;});
    }

    @After
    public void tearDown() throws Exception
    {
        Limpiador.getInstance().limpiarTabla();
    }

    @Test
    public void siSeRecuperaUnaUbicacionDeLaBaseDeDatosEstaEsConsistente()
    {
        //Setup(Given)
        Ubicacion ubicacionRecuperada;

        //Exercise(When)
        ubicacionRecuperada = Runner.runInSession(()-> { return ubicacionDAOSut.recuperar("El Origen"); });

        //Test(Then)
        assertEquals("El Origen", ubicacionRecuperada.getNombre());
        assertTrue(ubicacionRecuperada.getEntrenadores().isEmpty());
    }

    @Test
    public void siSeIntentaRecuperarUnaUbicacionQueNoExisteRetornaNull()
    {
        //Setup(Given)
        Ubicacion ubicacionRecuperada;

        //Exercise(When)
        ubicacionRecuperada = Runner.runInSession(()-> { return ubicacionDAOSut.recuperar("El Original"); });

        //Test(Then)
        assertNull(ubicacionRecuperada);
    }

    @Test
    public void siSeGuardaUnaUbicacionNuevaEstaSeGuardaCorrectamente()
    {
        //Setup(Given)
        Ubicacion nuevaUbicacion    = new Ubicacion();
        nuevaUbicacion.setNombre("Volcanus");
        Ubicacion ubicacionRecuperada;

        //Exercise(When)
        ubicacionRecuperada = Runner.runInSession(()-> {
                                                        ubicacionDAOSut.guardar(nuevaUbicacion);
                                                        return ubicacionDAOSut.recuperar("Volcanus");
                                                       });

        //Test(Then)
        assertEquals(nuevaUbicacion.getNombre(), ubicacionRecuperada.getNombre());
        assertEquals(nuevaUbicacion.getEntrenadores(), ubicacionRecuperada.getEntrenadores());
    }

    @Test
    public void siSeModificaUnaUbicacionGuardadaYSeActualizaSeHaceCorrectamente()
    {
        //Setup(Given)
        Ubicacion ubicacionRecuperada;
        Ubicacion ubicacionAModificar;
        ubicacionAModificar = Runner.runInSession(()-> { return ubicacionDAOSut.recuperar("El Origen");});
        Entrenador unNuevoEntrenador    = new Entrenador();
        unNuevoEntrenador.setNivel(2);
        unNuevoEntrenador.setExperiencia(15600);
        unNuevoEntrenador.setNombre("Rick");

        //Exercise(When)
        unNuevoEntrenador.setUbicacion(ubicacionAModificar);
        ubicacionAModificar.agregarEntrenador(unNuevoEntrenador);
        ubicacionRecuperada = Runner.runInSession(() -> {
                                                            ubicacionDAOSut.actualizar(ubicacionAModificar);
                                                            return ubicacionDAOSut.recuperar("El Origen");
                                                        });

        //Test(Then)
        assertEquals(ubicacionRecuperada.getNombre(), ubicacionAModificar.getNombre());
        assertEquals(ubicacionRecuperada.getEntrenadores(), ubicacionAModificar.getEntrenadores());
    }

    @Test(expected = PersistenceException.class)
    public void siSeintentaModificarElNombreUnaUbicacionGuardadaLanzaUnaExcepcion()
    {
        //Setup(Given)
        Runner.runInSession(()-> {
                                    Ubicacion aModificar    = ubicacionDAOSut.recuperar("El Origen");
                                    aModificar.setNombre("Missing Name");
                                    ubicacionDAOSut.guardar(aModificar);
                                    return null;
                                 });
        //Exercise(When)
        //Test(Then)
    }
}