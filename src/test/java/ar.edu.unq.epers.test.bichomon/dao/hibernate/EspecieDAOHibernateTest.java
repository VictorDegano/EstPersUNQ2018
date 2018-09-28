package ar.edu.unq.epers.test.bichomon.dao.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EspecieDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import ar.edu.unq.epers.bichomon.backend.service.EspecieServiceHibernate;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EspecieDAOHibernateTest {

    private EspecieServiceHibernate testService;
    EspecieDAOHibernate especieDAO;
    Especie especie;
    Especie especie2;
    Especie especie3;

    @Before
    public void setUp() throws Exception {
        especieDAO = new EspecieDAOHibernate();
        this.testService = new EspecieServiceHibernate(especieDAO);

        especie = new Especie(1, "arromon", TipoBicho.AGUA);
        especie.setAltura(12);
        especie.setPeso(100);
        especie.setEnergiaIncial(99);
        especie.setUrlFoto("/image/arromon.jpg");

        especie2 = new Especie(2, "ajimon", TipoBicho.FUEGO);
        especie2.setAltura(13);
        especie2.setPeso(110);
        especie2.setEnergiaIncial(98);
        especie2.setUrlFoto("/image/ajimon.jpg");

        especie3 = new Especie(3, "mugreomon", TipoBicho.TIERRA);
        especie3.setAltura(14);
        especie3.setPeso(120);
        especie3.setEnergiaIncial(97);
        especie3.setUrlFoto("/image/mugreomon.jpg");

        this.testService.crearEspecie(especie);
        this.testService.crearEspecie(especie2);
        this.testService.crearEspecie(especie3);

    }

    @After
    public void tearDown() throws Exception
    {
        Bootstrap bootstrap = new Bootstrap();
        Runner.runInSession(()-> {  bootstrap.limpiarTabla();
                                    return null;});
    }

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

    @Test
    public void es_service_recupera_todas_las_especies(){
        List<Especie> especiesRecuperadas = this.testService.getAllEspecies();

        assertEquals(3,especiesRecuperadas.size());
        assertEquals("ajimon", especiesRecuperadas.get(0).getNombre());
        assertEquals("arromon", especiesRecuperadas.get(1).getNombre());
        assertEquals("mugreomon", especiesRecuperadas.get(2).getNombre());

    }


    @Test
    public void al_crearse_un_bicho_se_incrementa_en_uno_la_cantidade_de_bichos_en_la_tabla_especie(){
        Especie especieRecuperada = this.testService.getEspecie("arromon");
        assertEquals(0,especieRecuperada.getCantidadBichos());
        Bicho bichoCreado = this.testService.crearBicho("arromon","arrozmon");
        Especie especieRecuperadaDespuesDeCrearBicho = this.testService.getEspecie("arromon");
        assertEquals(1,especieRecuperadaDespuesDeCrearBicho.getCantidadBichos());
    }


}

