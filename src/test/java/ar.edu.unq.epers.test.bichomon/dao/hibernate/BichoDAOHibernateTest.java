package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.BichoDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

public class BichoDAOHibernateTest {

    private BichoDAOHibernate bichoDAOSut;
    private Bootstrap bootstraper;

    @Before
    public void setUp() throws Exception
    {
        SessionFactoryProvider.getInstance();
        bichoDAOSut = new BichoDAOHibernate();
        bootstraper = new Bootstrap();

        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    return null;});
    }

    @After
    public void tearDown() throws Exception {   bootstraper.limpiarTabla(); }

    @Test
    public void siSeRecuperaUnBichoDeLaBaseDeDatosEstaEsConsistente()
    {
        //Setup(Given)
        Bicho bichoRecupeardo;

        //Exercise(When)
        bichoRecupeardo = Runner.runInSession(()-> { return bichoDAOSut.recuperar(1); });

        //Test(Then)
        assertEquals(1, bichoRecupeardo.getId());
        assertEquals(5555, bichoRecupeardo.getEnergia());
        assertEquals("Fortmon", bichoRecupeardo.getEspecie().getNombre());
        assertEquals("Pepe Pepon", bichoRecupeardo.getDuenio().getNombre());
    }

    @Test
    public void siSeIntentaRecuperarUnBichoQueNoExisteRetornaNull()
    {
        //Setup(Given)
        String mensaje  = "";
        Bicho bichoRecuperado;

        //Exercise(When)
        bichoRecuperado = Runner.runInSession(()-> { return bichoDAOSut.recuperar(32); });

        //Test(Then)
        assertNull(bichoRecuperado);
    }

    @Test
    public void siSeGuardaUnBichoNuevoEsteSeGuardaCorrectamente()
    {
        //Setup(Given)
        EspecieDAO unEspecieDAO = new EspecieDAOHibernate();
        Especie rojomon         = Runner.runInSession(()-> { return unEspecieDAO.recuperar("Rojomon");});
        Bicho nuevoBicho        = new Bicho(rojomon,"");
        nuevoBicho.setEnergia(90000);
        Bicho bichoRecuperado;

        //Exercise(When)
        bichoRecuperado = Runner.runInSession(()-> {bichoDAOSut.guardar(nuevoBicho);
                                                    return bichoDAOSut.recuperar(3);});

        //Test(Then)
        assertEquals(90000, bichoRecuperado.getEnergia());
        assertEquals(rojomon, bichoRecuperado.getEspecie());
        assertNull(bichoRecuperado.getDuenio());
    }

    @Test
    public void siSeModificaUnBichoGuardadoYSeActualizaSeHaceCorrectamente()
    {
        //Setup(Given)
        Bicho bichoRecuperado;
        Bicho bichoAModificar;
        bichoAModificar = Runner.runInSession(()-> { return bichoDAOSut.recuperar(1);});

        //Exercise(When)
        bichoAModificar.setEnergia(1);
        bichoRecuperado = Runner.runInSession(() -> {bichoDAOSut.actualizar(bichoAModificar);
                                                     return bichoDAOSut.recuperar(1);
        });

        //Test(Then)
        assertEquals(bichoRecuperado.getEnergia(), bichoAModificar.getEnergia());
        assertEquals(bichoRecuperado.getDuenio(), bichoAModificar.getDuenio());
        assertEquals(bichoRecuperado.getEspecie(), bichoAModificar.getEspecie());
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaModificarElIdDeUnBichoGuardadoPorUnoQueYaEstaLanzaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        Runner.runInSession(()-> {
                                    Bicho aModificar    = bichoDAOSut.recuperar(1);
                                    aModificar.setId(2);
                                    bichoDAOSut.actualizar(aModificar);
                                    return null;
                                });
        //Test(Then)
        fail("No hubo Excepcion");
    }

    @Test(expected = PersistenceException.class)
    public void siSeIntentaModificarLaIdDeUnBichoGuardadoLanzaUnaExcepcion()
    {
        //Setup(Given)
        //Exercise(When)
        Runner.runInSession(()-> {  Bicho aModificar    = bichoDAOSut.recuperar(1);
                                    aModificar.setId(25);
                                    bichoDAOSut.actualizar(aModificar);
                                    return null;});
        //Test(Then)
        fail("No hubo Excepcion");
    }
}