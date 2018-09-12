package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.EspecieDAOJDBC;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImplementation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataServiceImplementationTest {

    private Especie especiePrueba;
    private EspecieDAOJDBC especieDAOJDBC;
    private DataServiceImplementation dataServiceSUT;

    @Before
    public void setUp() throws Exception
    {

        especieDAOJDBC = new EspecieDAOJDBC();
        dataServiceSUT  = new DataServiceImplementation(especieDAOJDBC);
        especiePrueba   = new Especie(15, "Nievemon", TipoBicho.AGUA);
        especiePrueba.setAltura(12);
        especiePrueba.setPeso(100);
        especiePrueba.setEnergiaIncial(99);
        especiePrueba.setUrlFoto("/image/Nievemon.jpg");
    }

    @After
    public void tearDown() throws Exception
    {   }

    @Test
    public void dada_Una_Tabla_Con_Un_Elemento_Si_Limpio_La_Tabla_Y_Luego_Pido_El_Elemento_Devuelve_Null()
    {
        // Setup(Given)
        especieDAOJDBC.guardar(especiePrueba);
        // Exercise(When)
        dataServiceSUT.eliminarDatos();
        // Test(Then)
        assertNull(especieDAOJDBC.recuperar("Nievemon"));
    }

    @Test
    public void dada_una_tabla_vacia_si_creo_un_et_de_datos_iniciales_estos_son_correctos()
    {
        // Setup(Given)
        Especie rojomon;
        Especie amarillomon;
        Especie verdemon;
        Especie tierramon;
        Especie fantasmon;
        Especie vampiron;
        Especie fortmon;
        Especie dientemon;
        // Exercise(When)
        dataServiceSUT.crearSetDatosIniciales();
        rojomon     = especieDAOJDBC.recuperar("Rojomon");
        amarillomon = especieDAOJDBC.recuperar("Amarillomon");
        verdemon    = especieDAOJDBC.recuperar("Verdemon");
        tierramon   = especieDAOJDBC.recuperar("Tierramon");
        fantasmon   = especieDAOJDBC.recuperar("Fantasmon");
        vampiron    = especieDAOJDBC.recuperar("Vampiron");
        fortmon     = especieDAOJDBC.recuperar("Fortmon");
        dientemon   = especieDAOJDBC.recuperar("Dientemon");
        // Test(Then)
        assertEquals(Integer.valueOf(1), rojomon.getId());
        assertEquals("Rojomon", rojomon.getNombre());
        assertEquals(180, rojomon.getAltura());
        assertEquals(75, rojomon.getPeso());
        assertEquals(100, rojomon.getEnergiaInicial());
        assertEquals(TipoBicho.FUEGO, rojomon.getTipo());
        assertEquals("/image/rojomon.jpg", rojomon.getUrlFoto());
        assertEquals(0, rojomon.getCantidadBichos());

        assertEquals(Integer.valueOf(2), amarillomon.getId());
        assertEquals("Amarillomon", amarillomon.getNombre());
        assertEquals(170, amarillomon.getAltura());
        assertEquals(69, amarillomon.getPeso());
        assertEquals(300, amarillomon.getEnergiaInicial());
        assertEquals(TipoBicho.ELECTRICIDAD, amarillomon.getTipo());
        assertEquals("/image/amarillomon.png", amarillomon.getUrlFoto());
        assertEquals(0, amarillomon.getCantidadBichos());

        assertEquals(Integer.valueOf(3), verdemon.getId());
        assertEquals("Verdemon", verdemon.getNombre());
        assertEquals(150, verdemon.getAltura());
        assertEquals(55, verdemon.getPeso());
        assertEquals(5000, verdemon.getEnergiaInicial());
        assertEquals(TipoBicho.PLANTA, verdemon.getTipo());
        assertEquals("/image/verdemon.jpg", verdemon.getUrlFoto());
        assertEquals(0, verdemon.getCantidadBichos());

        assertEquals(Integer.valueOf(4), tierramon.getId());
        assertEquals("Tierramon", tierramon.getNombre());
        assertEquals(1050, tierramon.getAltura());
        assertEquals(99, tierramon.getPeso());
        assertEquals(5000, tierramon.getEnergiaInicial());
        assertEquals(TipoBicho.TIERRA, tierramon.getTipo());
        assertEquals("/image/tierramon.jpg", tierramon.getUrlFoto());
        assertEquals(0, tierramon.getCantidadBichos());

        assertEquals(Integer.valueOf(5), fantasmon.getId());
        assertEquals("Fantasmon", fantasmon.getNombre());
        assertEquals(1050, fantasmon.getAltura());
        assertEquals(99, fantasmon.getPeso());
        assertEquals(5000, fantasmon.getEnergiaInicial());
        assertEquals(TipoBicho.AIRE, fantasmon.getTipo());
        assertEquals("/image/fantasmon.jpg", fantasmon.getUrlFoto());
        assertEquals(0, fantasmon.getCantidadBichos());

        assertEquals(Integer.valueOf(6), vampiron.getId());
        assertEquals("Vampiron", vampiron.getNombre());
        assertEquals(1050, vampiron.getAltura());
        assertEquals(99, vampiron.getPeso());
        assertEquals(5000, vampiron.getEnergiaInicial());
        assertEquals(TipoBicho.AIRE, vampiron.getTipo());
        assertEquals("/image/vampiron.jpg", vampiron.getUrlFoto());
        assertEquals(0, vampiron.getCantidadBichos());

        assertEquals(Integer.valueOf(7), fortmon.getId());
        assertEquals("Fortmon", fortmon.getNombre());
        assertEquals(1050, fortmon.getAltura());
        assertEquals(99, fortmon.getPeso());
        assertEquals(5000, fortmon.getEnergiaInicial());
        assertEquals(TipoBicho.AIRE, fortmon.getTipo());
        assertEquals("/image/fortmon.png", fortmon.getUrlFoto());
        assertEquals(0, fortmon.getCantidadBichos());

        assertEquals(Integer.valueOf(8), dientemon.getId());
        assertEquals("Dientemon", dientemon.getNombre());
        assertEquals(1050, dientemon.getAltura());
        assertEquals(99, dientemon.getPeso());
        assertEquals(5000, dientemon.getEnergiaInicial());
        assertEquals(TipoBicho.AGUA, dientemon.getTipo());
        assertEquals("/image/dientemon.jpg", dientemon.getUrlFoto());
        assertEquals(0, dientemon.getCantidadBichos());

        //TearDown
        dataServiceSUT.eliminarDatos();
    }
}