package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

public class UbicacionDAOHibernateTest {

    private UbicacionDAOHibernate ubicacionDAOSut;
    private Bootstrap bootstraper;

    @Before
    public void setUp() throws Exception
    {
        SessionFactoryProvider.getInstance();
        ubicacionDAOSut     = new UbicacionDAOHibernate();
        bootstraper         = new Bootstrap();

        Runner.runInSession(()-> {bootstraper.crearDatos();
                                  return null;});
    }

    @After
    public void tearDown() throws Exception {   bootstraper.limpiarTabla(); }

    @Test
    public void siSeRecuperaUnaUbicacionDeLaBaseDeDatosEstaEsConsistente()
    {
        //Setup(Given)
        Ubicacion ubicacionRecuperada;

        //Exercise(When)
        ubicacionRecuperada = Runner.runInSession(()-> { return ubicacionDAOSut.recuperar("El Origen"); });

        //Test(Then)
        assertEquals("El Origen", ubicacionRecuperada.getNombre());
        assertTrue(! ubicacionRecuperada.getEntrenadores().isEmpty());
    }

    @Test
    public void siSeIntentaRecuperarUnaUbicacionQueNoExisteDaUnaExepcion()
    {
        //Setup(Given)
        String mensaje  = "";
        Ubicacion ubicacionRecuperada;

        //Exercise(When)
        try
        {   ubicacionRecuperada = Runner.runInSession(()-> { return ubicacionDAOSut.recuperar("El Original"); });   }
        catch(NoResultException e)
        {   mensaje = e.getMessage();   }

        //Test(Then)
        assertEquals("No entity found for query", mensaje);
    }

    @Test
    public void siSeGuardaUnaUbicacionNuevaEstaSeGuardaCorrectamente()
    {
        //Setup(Given)
        Ubicacion nuevaUbicacion    = new Pueblo();
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
    public void siSeIntentaModificarElIdDeUnaUbicacionGuardadaPorUnaQueYaEstaLanzaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        Runner.runInSession(()-> {
            Ubicacion aModificar    = ubicacionDAOSut.recuperar("El Origen");
            aModificar.setId(2);
            ubicacionDAOSut.actualizar(aModificar);
            return null;
        });
        //Test(Then)
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaModificarElIdDeUnaUbicacionGuardadaLanzaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        Runner.runInSession(()-> {
            Ubicacion aModificar    = ubicacionDAOSut.recuperar("El Origen");
            aModificar.setId(30);
            ubicacionDAOSut.actualizar(aModificar);
            return null;
        });
        //Test(Then)
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaModificarElNombreDeUnaUbicacionGuardadaPorUnaQueYaEstaLanzaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        Runner.runInSession(()-> {
            Ubicacion aModificar    = ubicacionDAOSut.recuperar("El Origen");
            aModificar.setNombre("Desert");
            ubicacionDAOSut.actualizar(aModificar);
            return null;
        });
        //Test(Then)
    }

    @Test
    public void siSeIntentaModificarElNombreDeUnaUbicacionGuardadaPorUnaQueNoEstaLaModifica()
    {
        //Setup(Given)
        Ubicacion ubicacionModificada;
        //Exercise(When)
        ubicacionModificada = Runner.runInSession(()-> {
                                    Ubicacion unaUbicacionRecuperada  = ubicacionDAOSut.recuperar("El Origen");
                                    unaUbicacionRecuperada.setNombre("El Nuevo Origen");
                                    ubicacionDAOSut.actualizar(unaUbicacionRecuperada);
                                    return ubicacionDAOSut.recuperar("El Nuevo Origen");
                                    });
        //Test(Then)
        assertEquals("El Nuevo Origen", ubicacionModificada.getNombre());
    }
}