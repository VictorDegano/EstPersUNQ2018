package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import ar.edu.unq.epers.bichomon.backend.service.EspecieServiceHibernate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EspecieDAOHibernateTest {

    private EspecieServiceHibernate testService;
    EspecieDAOHibernate especieDAO;
    Especie especie;

    @Before
    public void setUp() throws Exception {
        especieDAO = new EspecieDAOHibernate();
        this.testService = new EspecieServiceHibernate(especieDAO);
        TipoBicho tipo = TipoBicho.AGUA;
        especie = new Especie(11, "arromon", tipo);
        especie.setAltura(12);
        especie.setPeso(100);
        especie.setEnergiaIncial(99);
        especie.setUrlFoto("/image/arromon.jpg");
        this.testService.crearEspecie(especie);


    }

    //@After
    //public void tearDown() throws Exception {
    //}

    @Test
    public void cuando_se_guarda_una_especie_se_hace_de_forma_correcta() {

        Especie especie2 = this.testService.getEspecie("arromon");

        assertEquals( especie2.getNombre(), especie.getNombre());
        assertEquals( especie2.getId(), especie.getId());
        assertEquals( especie2.getTipo(), especie.getTipo());
        assertEquals( especie2.getAltura(), especie.getAltura());
        assertEquals( especie2.getPeso(), especie.getPeso());
        assertEquals( especie2.getEnergiaInicial(), especie.getEnergiaInicial());
        assertEquals( especie2.getUrlFoto(), especie.getUrlFoto());


    }


}

