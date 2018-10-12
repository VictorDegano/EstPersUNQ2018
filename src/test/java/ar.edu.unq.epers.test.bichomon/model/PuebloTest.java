package ar.edu.unq.epers.test.bichomon.model;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.ProbabilidadDeEspecie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.*;


public class PuebloTest {

    private Especie red;
    private Especie amarillo;
    private Especie green;
    private @Spy Pueblo pueblo;
    private ProbabilidadDeEspecie probRed;
    private ProbabilidadDeEspecie probAmar;
    private ProbabilidadDeEspecie probGreen;
    private List<ProbabilidadDeEspecie> probabilidadDeEspecies;
    private Entrenador entrenador1;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Nivel nivel1  = new Nivel(1, 1, 99, 4);
        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(0);
        entrenador1.setNivel(nivel1);
        red = new Especie();
        red.setNombre("Rojomon");
        red.setTipo(TipoBicho.FUEGO);
        red.setAltura(180);
        red.setPeso(75);
        red.setEnergiaIncial(100);
        red.setUrlFoto("/image/rojomon.jpg");

        amarillo = new Especie();
        amarillo.setNombre("Amarillomon");
        amarillo.setTipo(TipoBicho.ELECTRICIDAD);
        amarillo.setAltura(170);
        amarillo.setPeso(69);
        amarillo.setEnergiaIncial(300);
        amarillo.setUrlFoto("/image/amarillomon.png");

        green = new Especie();
        green.setNombre("Verdemon");
        green.setTipo(TipoBicho.PLANTA);
        green.setAltura(150);
        green.setPeso(55);
        green.setEnergiaIncial(5000);
        green.setUrlFoto("/image/verdemon.jpg");



        pueblo       = spy(new Pueblo());
        pueblo.setNombre("La Prueba");

        probRed = new ProbabilidadDeEspecie();
        probRed.setEspecie(red);
        probRed.setPosibilidad(30);

       probAmar = new ProbabilidadDeEspecie();
        probAmar.setEspecie(amarillo);
        probAmar.setPosibilidad(40);

        probGreen = new ProbabilidadDeEspecie();
        probGreen.setEspecie(red);
        probGreen.setPosibilidad(30);

        probabilidadDeEspecies = new ArrayList<ProbabilidadDeEspecie>();
        probabilidadDeEspecies.add(probRed);
        probabilidadDeEspecies.add(probAmar);
        probabilidadDeEspecies.add(probGreen);

        pueblo.setProbabilidadDeEspeciesDelPueblo(probabilidadDeEspecies);

    }

    @After
    public void tearDown() throws Exception {   }

    @Test
    public void buscarEspecie() {
        // Obligo a que salga el primero
        when(pueblo.probabilidad()).thenReturn(0);
        Bicho bicho = pueblo.buscarBicho(entrenador1);
        assertEquals(bicho.getEspecie().getNombre(),"Rojomon");
    }
}