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

import static org.junit.Assert.*;

public class CondicionesDeEvolucionTest {

    private CondicionEnergia condicionEnergiaSut;
    private CondicionVictoria condicionVictoriaSut;
    private CondicionEdad condicionEdadSut;
    private CondicionNivel condicionNivelSut;
    private Bicho bichoCumplidor;
    private Bicho bichoIncumplidor;

    @Before
    public void setUp() throws Exception
    {
        Nivel nivel5  = new Nivel(5, 2000, 2999, 8);
        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(2555);
        entrenador1.setNivel(nivel5);

        Nivel nivel3  = new Nivel(3, 400, 999, 6);
        Entrenador entrenador2  = new Entrenador();
        entrenador2.setNombre("El Loquillo");
        entrenador2.setExperiencia(990);
        entrenador2.setNivel(nivel3);

        Especie unaEspecieRoja  = new Especie();
        unaEspecieRoja.setNombre("Rojomon");
        unaEspecieRoja.setTipo(TipoBicho.FUEGO);
        unaEspecieRoja.setAltura(180);
        unaEspecieRoja.setPeso(75);
        unaEspecieRoja.setEnergiaIncial(100);
        unaEspecieRoja.setUrlFoto("/image/rojomon.jpg");
        unaEspecieRoja.setCantidadBichos(1);

        Especie unaEspecieFort  = new Especie();
        unaEspecieFort.setNombre("Fortmon");
        unaEspecieFort.setTipo(TipoBicho.CHOCOLATE);
        unaEspecieFort.setAltura(1050);
        unaEspecieFort.setPeso(99);
        unaEspecieFort.setEnergiaIncial(5000);
        unaEspecieFort.setUrlFoto("/image/fortmon.png");
        unaEspecieFort.setCantidadBichos(1);

        bichoCumplidor  = new Bicho();
        bichoCumplidor.setEnergia(unaEspecieFort.getEnergiaInicial());
        bichoCumplidor.setDuenio(entrenador1);
        bichoCumplidor.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now().minusDays(40)));
        bichoCumplidor.setVictorias(22);

        bichoIncumplidor  = new Bicho();
        bichoIncumplidor.setEnergia(unaEspecieRoja.getEnergiaInicial());
        bichoIncumplidor.setDuenio(entrenador2);
        bichoIncumplidor.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.now()));
        bichoIncumplidor.setVictorias(0);

        condicionEnergiaSut = new CondicionEnergia();
        condicionEnergiaSut.setEnergiaCondicion(4000);

        condicionVictoriaSut= new CondicionVictoria();
        condicionVictoriaSut.setVictoriaCondicion(5);

        condicionEdadSut    = new CondicionEdad();
        condicionEdadSut.setEdadCondicion(30);

        condicionNivelSut   = new CondicionNivel();
        condicionNivelSut.setNivelCondicion(4);
    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void SiUnaCondicionDeEnergiaPideSuperarLos4000YElBichoNoCumpleDaFalso()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertFalse(condicionEnergiaSut.cumpleCondicion(bichoIncumplidor));
    }

    @Test
    public void SiUnaCondicionDeEnergiaPideSuperarLos4000YElBichoCumpleDaTrue()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertTrue(condicionEnergiaSut.cumpleCondicion(bichoCumplidor));
    }

    @Test
    public void SiUnaCondicionDeVictoriasPideSuperarLa5VictoriasYElBichoNoCumpleDaFalso()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertFalse(condicionVictoriaSut.cumpleCondicion(bichoIncumplidor));
    }

    @Test
    public void SiUnaCondicionDeVictoriasPideSuperarLa5VictoriasYElBichoCumpleDaTrue()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertTrue(condicionVictoriaSut.cumpleCondicion(bichoCumplidor));
    }

    @Test
    public void SiUnaCondicionDeEdadPideSuperarLas30DiasYElBichoNoCumpleDaFalso()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertFalse(condicionEdadSut.cumpleCondicion(bichoIncumplidor));
    }

    @Test
    public void SiUnaCondicionDeEdadPideSuperarLas30DiasYElBichoCumpleDaTrue()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertTrue(condicionEdadSut.cumpleCondicion(bichoCumplidor));
    }

    @Test
    public void SiUnaCondicionDeNivelPideSuperarElNivel4YElBichoNoCumpleDaFalso()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertFalse(condicionNivelSut.cumpleCondicion(bichoIncumplidor));
    }

    @Test
    public void SiUnaCondicionDeNivelPideSuperarElNivel4YElBichoCumpleDaTrue()
    {
        //Setup(Given)
        //Exercise(When)
        //Test(Then)
        assertTrue(condicionNivelSut.cumpleCondicion(bichoCumplidor));
    }
}