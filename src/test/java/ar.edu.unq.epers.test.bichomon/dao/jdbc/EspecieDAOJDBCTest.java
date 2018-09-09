package ar.edu.unq.epers.test.bichomon.dao.jdbc;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImplementation;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.EspecieDAOJDBC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EspecieDAOJDBCTest {

    private EspecieDAOJDBC especieDAOJDBCSut;
    private Especie     especiePrueba;
    private DataServiceImplementation dataService;

    @Before
    public void setUp() throws Exception
    {
        especieDAOJDBCSut = new EspecieDAOJDBC();
        dataService     = new DataServiceImplementation(especieDAOJDBCSut);
        especiePrueba   = new Especie(15, "Nievemon", TipoBicho.AGUA);
        especiePrueba.setAltura(12);
        especiePrueba.setPeso(100);
        especiePrueba.setEnergiaIncial(99);
        especiePrueba.setUrlFoto("/image/Nievemon.jpg");
        dataService.crearSetDatosIniciales();
    }

    @After
    public void tearDown() throws Exception
    {   dataService.eliminarDatos();    }


    @Test
    public void cuando_se_recupera_una_especie_esta_tiene_los_datos_correctos() {
        // Setup(Given)
        Especie especieDesdeBD;

        // Exercise(Then)
        especieDesdeBD = especieDAOJDBCSut.recuperar("Fortmon");

        // Test(When)
        assertEquals(Integer.valueOf(7), especieDesdeBD.getId());
        assertEquals("Fortmon", especieDesdeBD.getNombre());
        assertEquals(1050, especieDesdeBD.getAltura());
        assertEquals(99, especieDesdeBD.getPeso());
        assertEquals(TipoBicho.AIRE, especieDesdeBD.getTipo());
        assertEquals("/image/fortmon.png", especieDesdeBD.getUrlFoto());
        assertEquals(5000, especieDesdeBD.getEnergiaInicial());
        assertEquals(0, especieDesdeBD.getCantidadBichos());
    }

    @Test
    public void cuando_se_guarda_una_especie_se_hace_de_forma_correcta() {
        // Setup(Given)
        Especie especieDesdeBD = null;

        // Exercise(Then)
        especieDAOJDBCSut.guardar(especiePrueba);
        especieDesdeBD = especieDAOJDBCSut.recuperar("Nievemon");

        // Test(When)
        assertEquals(especiePrueba.getId(), especieDesdeBD.getId());
        assertEquals(especiePrueba.getNombre(), especieDesdeBD.getNombre());
        assertEquals(especiePrueba.getAltura(), especieDesdeBD.getAltura());
        assertEquals(especiePrueba.getPeso(), especieDesdeBD.getPeso());
        assertEquals(especiePrueba.getTipo(), especieDesdeBD.getTipo());
        assertEquals(especiePrueba.getUrlFoto(), especieDesdeBD.getUrlFoto());
        assertEquals(especiePrueba.getEnergiaInicial(), especieDesdeBD.getEnergiaInicial());
        assertEquals(especiePrueba.getCantidadBichos(), especieDesdeBD.getCantidadBichos());
    }

    @Test
    public void cuandoSeActualizaUnaEspecieSeHaceDeFormaCorrecta() {
        //Nueva especie para actualizar
        Especie especieTest = new Especie(15, "pikachu", TipoBicho.ELECTRICIDAD);
        especieTest.setAltura(12);
        especieTest.setPeso(100);
        especieTest.setEnergiaIncial(99);
        especieTest.setUrlFoto("/image/Nievemon.jpg");

        // Exercise(Then)
        especieDAOJDBCSut.guardar(especiePrueba);
        especieDAOJDBCSut.actualizar(especieTest);
        Especie especieDesdeDB = especieDAOJDBCSut.recuperar("pikachu");

        //Test (WHEN)
        assertEquals(especieTest.getId(), especieDesdeDB.getId());
        assertEquals(especieTest.getNombre(), especieDesdeDB.getNombre());
        assertEquals(especieTest.getAltura(), especieDesdeDB.getAltura());
        assertEquals(especieTest.getPeso(), especieDesdeDB.getPeso());
        assertEquals(especieTest.getTipo(), especieDesdeDB.getTipo());
        assertEquals(especieTest.getUrlFoto(), especieDesdeDB.getUrlFoto());
        assertEquals(especieTest.getEnergiaInicial(), especieDesdeDB.getEnergiaInicial());
        assertEquals(especieTest.getCantidadBichos(), especieDesdeDB.getCantidadBichos());
        especieDAOJDBCSut.borrarEspecie("pikachu");
    }

    @Test(expected = RuntimeException.class)
    public void cuando_se_guarda_una_especie_existente_la_Base_Chilla() {
        // Setup(Given)
        // Exercise(Then)
        especieDAOJDBCSut.guardar(especiePrueba);
        Especie copiaE  = especiePrueba;
        especieDAOJDBCSut.guardar(copiaE);

        // Test(When)
        assertTrue(true);
    }

    @Test
    public void seRecuperanTodasLasEspeciesDeLaBaseDeDatos(){
        List<Especie> especies = especieDAOJDBCSut.recuperarTodos();
        List<String> nombreEspecies = new ArrayList<String>();
        nombreEspecies.add("Amarillomon");
        nombreEspecies.add("Dientemon");
        nombreEspecies.add("Fantasmon");
        nombreEspecies.add("Fortmon");
        nombreEspecies.add("Rojomon");
        nombreEspecies.add("Tierramon");
        nombreEspecies.add("Vampiron");
        nombreEspecies.add("Verdemon");
        assertTrue(nombreEspecies.contains(especies.get(0).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(1).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(2).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(3).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(4).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(5).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(6).getNombre()));
        assertTrue(nombreEspecies.contains(especies.get(7).getNombre()));
        assertEquals(8,especies.size());
    }

}

