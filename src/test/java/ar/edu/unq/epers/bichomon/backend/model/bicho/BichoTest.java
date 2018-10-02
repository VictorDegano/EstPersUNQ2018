package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEnergia;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionVictoria;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BichoTest {

    private Especie lagartomon;
    private Especie reptilmon;
    private Especie sinevomon;
    private Bicho bichoSut;
    private Bicho bichoSutSinEvo;

    @Before
    public void setUp() throws Exception {
        sinevomon   = new Especie();
        sinevomon.setNombre("Sinevomon");
        sinevomon.setCantidadBichos(1);
        sinevomon.setEnergiaIncial(555);
        sinevomon.setEspecieBase(sinevomon);

        bichoSutSinEvo  = new Bicho();
        bichoSutSinEvo.setEspecie(sinevomon);
        bichoSutSinEvo.setEnergia(sinevomon.getEnergiaInicial());
        bichoSutSinEvo.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now()));
        bichoSutSinEvo.setVictorias(0);

        lagartomon  = new Especie();
        lagartomon.setNombre("Lagartomon");
        lagartomon.setTipo(TipoBicho.TIERRA);
        lagartomon.setAltura(20);
        lagartomon.setPeso(22);
        lagartomon.setEnergiaIncial(100);
        lagartomon.setUrlFoto("/image/Lagartomon.jpg");
        lagartomon.setCantidadBichos(1);
        lagartomon.setEspecieBase(lagartomon);

        reptilmon   = new Especie();
        reptilmon.setNombre("Reptilmon");
        reptilmon.setTipo(TipoBicho.TIERRA);
        reptilmon.setAltura(50);
        reptilmon.setPeso(42);
        reptilmon.setEnergiaIncial(500);
        reptilmon.setUrlFoto("/image/Reptilmon.jpg");
        reptilmon.setCantidadBichos(0);
        reptilmon.setEspecieBase(lagartomon);

        lagartomon.setEvolucion(reptilmon);

        bichoSut = new Bicho();
        bichoSut.setEspecie(lagartomon);
        bichoSut.setEnergia(lagartomon.getEnergiaInicial());
        bichoSut.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now().minusDays(30)));
        bichoSut.setVictorias(10);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void SiUnBichoNoTieneEvolucionNoEvolucionaYSuEnergiaNoSeModifica()
    {
        //Setup(Given)
        //Exercise(When)
        bichoSutSinEvo.evolucionar();
        //Test(Then)
        assertEquals(sinevomon, bichoSutSinEvo.getEspecie());
        assertEquals(555, bichoSutSinEvo.getEnergia());
    }

    @Test
    public void SiUnBichoTieneEvolucionSuEnergiaEstaPorDebajoDeLaEvolucionYCumpleLasCondicionesEvolucionaYSuEnergiaSeAumenta()
    {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(90);

        CondicionVictoria condicionVictoria = new CondicionVictoria();
        condicionVictoria.setVictoriaCondicion(2);

        lagartomon.setCondicionesDeEvolucion(Arrays.asList(condicionEnergia,condicionVictoria));
        //Exercise(When)
        bichoSut.evolucionar();

        //Test(Then)
        assertEquals(reptilmon, bichoSut.getEspecie());
        assertEquals(500, bichoSut.getEnergia());
        assertEquals(0, lagartomon.getCantidadBichos());
        assertEquals(1, reptilmon.getCantidadBichos());
    }


}