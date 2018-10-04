package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EspecieTest {

    private Especie unaEspecieSut;
    private Bicho unBicho;

    @Before
    public void setUp() throws Exception
    {
        Nivel nivel5  = new Nivel(5, 2000, 2999, 8);
        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(2555);
        entrenador1.setNivel(nivel5);

        unaEspecieSut = new Especie();
        unaEspecieSut.setNombre("Fortmon");
        unaEspecieSut.setTipo(TipoBicho.CHOCOLATE);
        unaEspecieSut.setAltura(1050);
        unaEspecieSut.setPeso(99);
        unaEspecieSut.setEnergiaIncial(5000);
        unaEspecieSut.setUrlFoto("/image/fortmon.png");
        unaEspecieSut.setCantidadBichos(1);

        Especie evolucion = new Especie();
        unaEspecieSut.setEvolucion(evolucion);

        unBicho = new Bicho();
        unBicho.setEnergia(unaEspecieSut.getEnergiaInicial());
        unBicho.setDuenio(entrenador1);
        unBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now().minusDays(40)));
        unBicho.setVictorias(22);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void DadasUnaCondicionesDeEvolucionDeEnergiaYNivelSiElBichoNoCumpleNingunaDaFalso()
    {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(10000);
        CondicionNivel condicionNivel       = new CondicionNivel();
        condicionNivel.setNivelCondicion(10);
        List<CondicionEvolucion> condiciones= new ArrayList<>();
        condiciones.add(condicionEnergia);
        condiciones.add(condicionNivel);
        unaEspecieSut.setCondicionesDeEvolucion(condiciones);

        //Exercise(When)
        //Test(Then)
        assertFalse(unaEspecieSut.puedeEvolucionar(unBicho));
    }

    @Test
    public void DadasUnaCondicionesDeEvolucionDeEnergiaYNivelSiElBichoNoCumpleAlgunaDaFalso() {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(6000);
        CondicionNivel condicionNivel       = new CondicionNivel();
        condicionNivel.setNivelCondicion(1);
        List<CondicionEvolucion> condiciones= new ArrayList<>();
        condiciones.add(condicionEnergia);
        condiciones.add(condicionNivel);
        unaEspecieSut.setCondicionesDeEvolucion(condiciones);

        //Exercise(When)
        //Test(Then)
        assertFalse(unaEspecieSut.puedeEvolucionar(unBicho));
    }

    @Test
    public void DadasUnaCondicionesDeEvolucionDeEnergiaYNivelSiElBichoCumpleTodasDaVerdadero() {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(4000);
        CondicionNivel condicionNivel       = new CondicionNivel();
        condicionNivel.setNivelCondicion(3);
        List<CondicionEvolucion> condiciones= new ArrayList<>();
        condiciones.add(condicionEnergia);
        condiciones.add(condicionNivel);
        unaEspecieSut.setCondicionesDeEvolucion(condiciones);

        //Exercise(When)
        //Test(Then)
        assertTrue(unaEspecieSut.puedeEvolucionar(unBicho));
    }

    @Test
    public void DadasUnaEspecieConLasCuatroCondicionesDeEvolucionSiElBichoNoCumpleAlgunaDaFalso() {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(4000);
        CondicionNivel condicionNivel       = new CondicionNivel();
        condicionNivel.setNivelCondicion(3);
        CondicionEdad condicionEdad         = new CondicionEdad();
        condicionEdad.setEdadCondicion(10);
        CondicionVictoria condicionVictoria = new CondicionVictoria();
        condicionVictoria.setVictoriaCondicion(1596749);

        List<CondicionEvolucion> condiciones= new ArrayList<>();
        condiciones.add(condicionEnergia);
        condiciones.add(condicionNivel);
        condiciones.add(condicionEdad);
        condiciones.add(condicionVictoria);
        unaEspecieSut.setCondicionesDeEvolucion(condiciones);

        //Exercise(When)
        //Test(Then)
        assertFalse(unaEspecieSut.puedeEvolucionar(unBicho));
    }

    @Test
    public void DadasUnaEspecieConLasCuatroCondicionesDeEvolucionSiElBichoCumpleTodasDaVerdadero() {
        //Setup(Given)
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(2000);
        CondicionNivel condicionNivel       = new CondicionNivel();
        condicionNivel.setNivelCondicion(2);
        CondicionEdad condicionEdad         = new CondicionEdad();
        condicionEdad.setEdadCondicion(5);
        CondicionVictoria condicionVictoria = new CondicionVictoria();
        condicionVictoria.setVictoriaCondicion(1);

        List<CondicionEvolucion> condiciones= new ArrayList<>();
        condiciones.add(condicionEnergia);
        condiciones.add(condicionNivel);
        condiciones.add(condicionEdad);
        condiciones.add(condicionVictoria);
        unaEspecieSut.setCondicionesDeEvolucion(condiciones);

        //Exercise(When)
        //Test(Then)
        assertTrue(unaEspecieSut.puedeEvolucionar(unBicho));
    }

    @Test
    public void SiUnaEspecieNoTieneEvolucionNoImportaSiCumpleConCondicionesONoElBichoNoPuedeEvolucionar() {

        //Setup(Given) I
        unaEspecieSut.setEvolucion(null);
        //Exercise(When) I
        //Test(Then) I
        assertFalse(unaEspecieSut.puedeEvolucionar(unBicho));

        //Setup(Given) II
        CondicionEnergia condicionEnergia   = new CondicionEnergia();
        condicionEnergia.setEnergiaCondicion(2000);
        CondicionVictoria condicionVictoria = new CondicionVictoria();
        condicionVictoria.setVictoriaCondicion(1);

        unaEspecieSut.setCondicionesDeEvolucion(Arrays.asList(condicionEnergia,condicionVictoria));

        //Exercise(When) II
        //Test(Then) II
        assertFalse(unaEspecieSut.puedeEvolucionar(unBicho));
    }
}