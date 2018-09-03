package ar.edu.unq.epers.test.bichomon;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataServiceImplementation;
import ar.edu.unq.epers.bichomon.frontend.dao.EspecieDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EspecieDAOTest {

    private EspecieDAO  especieDAOSut;
    private Especie     especiePrueba;
    private DataServiceImplementation dataService;

    @Before
    public void setUp() throws Exception
    {
        dataService     = new DataServiceImplementation();
        especieDAOSut   = new EspecieDAO();
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
        especieDesdeBD = especieDAOSut.recuperar("Fortmon");

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
        especieDAOSut.guardar(especiePrueba);
        especieDesdeBD = especieDAOSut.recuperar("Nievemon");

        // Test(When)
        assertEquals(especiePrueba.getId(), especieDesdeBD.getId());
        assertEquals(especiePrueba.getNombre(), especieDesdeBD.getNombre());
        assertEquals(especiePrueba.getAltura(), especieDesdeBD.getAltura());
        assertEquals(especiePrueba.getPeso(), especieDesdeBD.getPeso());
        assertEquals(especiePrueba.getTipo(), especieDesdeBD.getTipo());
        assertEquals(especiePrueba.getUrlFoto(), especieDesdeBD.getUrlFoto());
        assertEquals(especiePrueba.getEnergiaInicial(), especieDesdeBD.getEnergiaInicial());
        assertEquals(especiePrueba.getCantidadBichos(), especieDesdeBD.getCantidadBichos());

        //TEAR DOWN
        especieDAOSut.borrarEspecie("Nievemon");
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
        especieDAOSut.guardar(especiePrueba);
        especieDAOSut.actualizar(especieTest);
        Especie especieDesdeDB = especieDAOSut.recuperar("pikachu");

        //Test (WHEN)
        assertEquals(especieTest.getId(), especieDesdeDB.getId());
        assertEquals(especieTest.getNombre(), especieDesdeDB.getNombre());
        assertEquals(especieTest.getAltura(), especieDesdeDB.getAltura());
        assertEquals(especieTest.getPeso(), especieDesdeDB.getPeso());
        assertEquals(especieTest.getTipo(), especieDesdeDB.getTipo());
        assertEquals(especieTest.getUrlFoto(), especieDesdeDB.getUrlFoto());
        assertEquals(especieTest.getEnergiaInicial(), especieDesdeDB.getEnergiaInicial());
        assertEquals(especieTest.getCantidadBichos(), especieDesdeDB.getCantidadBichos());
        especieDAOSut.borrarEspecie("pikachu");
    }

    @Test
    public void seRecuperanTodasLasEspeciesDeLaBaseDeDatos(){
        List<Especie> especies = especieDAOSut.recuperarTodos();
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

