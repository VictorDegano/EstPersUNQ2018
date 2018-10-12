package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.ExperienciaDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Experiencia;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.TipoExperiencia;
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
        Experiencia combatir    = new Experiencia(TipoExperiencia.COMBATE, 10);
        Experiencia capturar    = new Experiencia(TipoExperiencia.CAPTURA, 10);
        Experiencia evolucion   = new Experiencia(TipoExperiencia.EVOLUCION, 5);
        experienciaDAOSUT       = new ExperienciaDAOHibernate();
        Runner.runInSession(()-> {
                                    Session session = Runner.getCurrentSession();
                                    session.save(capturar);
                                    session.save(combatir);
                                    session.save(evolucion);
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
        Experiencia capturar;
        Experiencia combate;
        Experiencia evolucion;
        //Exercise(When)
        capturar    = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar(TipoExperiencia.COMBATE);});
        combate     = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar(TipoExperiencia.CAPTURA);});
        evolucion   = Runner.runInSession(()-> { return experienciaDAOSUT.recuperar(TipoExperiencia.EVOLUCION);});
        //Test(Then)
        assertEquals(10, combate.getExperiencia());
        assertEquals(10, capturar.getExperiencia());
        assertEquals(5, evolucion.getExperiencia());
    }
}