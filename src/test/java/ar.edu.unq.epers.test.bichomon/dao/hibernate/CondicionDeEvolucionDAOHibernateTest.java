package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.CondicionDeEvolucionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CondicionDeEvolucionDAOHibernateTest {

    private CondicionDeEvolucionDAO condicionDeEvolucionDAOSut;
    private CondicionEnergia condicionEnergia;
    private CondicionVictoria condicionVictoria;
    private CondicionNivel condicionNivel;
    private CondicionEdad condicionEdad;
    private Bootstrap bootstraper;

    @Before
    public void setUp() throws Exception
    {
        condicionDeEvolucionDAOSut  = new CondicionDeEvolucionDAOHibernate();

        condicionEnergia = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(40);

        condicionVictoria= new CondicionVictoria();
        condicionVictoria.setVictoriaCondicion(20);

        condicionEdad    = new CondicionEdad();
        condicionEdad.setEdadCondicion(3);

        condicionNivel   = new CondicionNivel();
        condicionNivel.setNivelCondicion(2);

        bootstraper = new Bootstrap();

        Runner.runInSession(()-> {  bootstraper.crearDatos();
                                    return null;});
    }

    @After
    public void tearDown() throws Exception {   bootstraper.limpiarTabla(); }

    @Test
    public void siSeRecuperaUnaCondicionEstaEsConsistente()
    {
        //Setup(Given)
        CondicionEnergia condicionRecupeardo1;
        CondicionVictoria condicionRecupeardo2;

        //Exercise(When)
        condicionRecupeardo1 = (CondicionEnergia) Runner.runInSession(()-> { return condicionDeEvolucionDAOSut.recuperar(1); });
        condicionRecupeardo2 = (CondicionVictoria) Runner.runInSession(()-> { return condicionDeEvolucionDAOSut.recuperar(2); });

        //Test(Then)
        assertEquals(1, condicionRecupeardo1.getId());
        assertEquals(332, condicionRecupeardo1.getEnergiaCondicion());
        assertEquals(2, condicionRecupeardo2.getId());
        assertEquals(5, condicionRecupeardo2.getVictoriaCondicion());
    }

    @Test
    public void siSeIntentaRecuperarUnaCondicionInexistenteDevuelveNull()
    {
        //Setup(Given)
        CondicionEvolucion condicionRecupeardo;

        //Exercise(When)
        condicionRecupeardo = Runner.runInSession(()-> { return condicionDeEvolucionDAOSut.recuperar(55); });

        //Test(Then)
        assertNull(condicionRecupeardo);
    }

    @Test
    public void siSeGuardaUnaNuevaCondicionEstaSeGuardaCorrectamente()
    {
        //Setup(Given)
        CondicionNivel condicionRecuperado;

        //Exercise(When)
        condicionRecuperado = (CondicionNivel) Runner.runInSession(()-> {condicionDeEvolucionDAOSut.guardar(condicionNivel);
                                                                         return condicionDeEvolucionDAOSut.recuperar(3); });

        //Test(Then)
        assertEquals(3, condicionRecuperado.getId());
        assertEquals(2, condicionRecuperado.getNivelCondicion());
    }
}