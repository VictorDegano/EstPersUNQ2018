package ar.edu.unq.epers.test.bichomon.dao;

import ar.edu.unq.epers.bichomon.backend.dao.ExperienciaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.ExperienciaDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExperienciaDAOHibernateTest {

    private ExperienciaDAOHibernate experienciaDAOSUT;

    @Before
    public void setUp() throws Exception
    {
        experienciaDAOSUT   = new ExperienciaDAOHibernate();
        Runner.runInSession(()-> {
                                    Session session = Runner.getCurrentSession();
                                    session.createNativeQuery("CREATE TABLE IF NOT EXISTS Experiencia (nombre VARCHAR(255) NOT NULL UNIQUE, experiencia INTEGER, PRIMARY KEY(nombre));").executeUpdate();
                                    session.createNativeQuery("INSERT INTO Experiencia (nombre, experiencia) VALUES ('COMBATIR', 10),\n" +
                                                                                                                            "('CAPTURAR', 10),\n" +
                                                                                                                            "('EVOLUCION', 5);").executeUpdate();
                                    return null;
                                });
    }

    @After
    public void tearDown() throws Exception
    {
        Runner.runInSession(()-> {  Session session = Runner.getCurrentSession();
                                    session.createNativeQuery("TRUNCATE TABLE Experiencia;").executeUpdate();
                                    return null;
                                 });
    }

    @Test
    public void DadaUnaTablaDeExperienciaSiRecuperoLosValoresDeExperienciaDeCombateEvolucionYCapturarLosObtengoCorrectamente()
    {
        //Setup(Given)
        int capturar;
        int combate;
        int evolucion;
        //Exercise(When)
        capturar    = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar("CAPTURAR");});
        combate     = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar("COMBATIR");});
        evolucion   = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar("EVOLUCUION");});
        //Test(Then)
        assertEquals(10, combate);
        assertEquals(10, capturar);
        assertEquals(5, evolucion);
    }
}