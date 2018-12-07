package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOBicho;
import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOEntrenador;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.BichoDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.busqueda.BusquedaServiceImplementation;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import org.elasticsearch.action.search.SearchResponse;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BusquedaServiceImplementacionTest {
    private ElasticSearchDAOEntrenador elasticSearchDAOSUTEntrenador;
    private Entrenador unEntrenadorAIndexar;
    private Nivel nivel10;
    private List<Bicho> bichos;
    private Ubicacion puebloElOrigen;
    private Entrenador otroEntrenadorAIndexar;
    private Entrenador otroEntrenadorMasAIndexar;
    private EntrenadorDAOHibernate entrenadorDAOHibernate;
    private ElasticSearchDAOBicho elasticSearchDAOBicho;
    private BichoDAOHibernate bichoDAOHibernate;
    private Bootstrap bootstraper;
    private BusquedaServiceImplementation busquedaServiceImplementacion;
    @Before
    public void setUp() throws Exception {
        this.elasticSearchDAOSUTEntrenador = new ElasticSearchDAOEntrenador();
        this.entrenadorDAOHibernate = new EntrenadorDAOHibernate();
        this.elasticSearchDAOBicho = new ElasticSearchDAOBicho();
        this.bichoDAOHibernate = new BichoDAOHibernate();
        this.bootstraper = new Bootstrap();
        this.busquedaServiceImplementacion = new BusquedaServiceImplementation(entrenadorDAOHibernate,bichoDAOHibernate,elasticSearchDAOBicho,elasticSearchDAOSUTEntrenador);


        unEntrenadorAIndexar = new Entrenador();
        unEntrenadorAIndexar.setNombre("Marcelo Tinelli");
        nivel10 = new Nivel(10, 7000, 7999, 13);


        otroEntrenadorAIndexar = new Entrenador();
        otroEntrenadorAIndexar.setNombre("Miguel");

        otroEntrenadorMasAIndexar = new Entrenador();
        otroEntrenadorMasAIndexar.setNombre("Nestor");

        Especie unaEspecie = new Especie();
        unaEspecie.setNombre("Rojomon");

        puebloElOrigen = new Pueblo();
        puebloElOrigen.setNombre("El Origen");

        Bicho unBicho = new Bicho();
        unBicho.setNombre("pikachu");
        unBicho.setId(24);
        unBicho.setVictorias(3);
        unBicho.setDuenio(unEntrenadorAIndexar);
        unBicho.setEspecie(unaEspecie);
        unBicho.setEnergia(1500);
        unBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1998, 12, 11, 23, 22, 2)));

        Bicho otroBicho = new Bicho();
        otroBicho.setNombre("charmander");
        otroBicho.setId(34);
        otroBicho.setVictorias(3);
        otroBicho.setDuenio(unEntrenadorAIndexar);
        otroBicho.setEspecie(unaEspecie);
        otroBicho.setEnergia(1500);
        otroBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1992, 12, 11, 23, 22, 2)));

        bichos = new ArrayList<Bicho>();
        bichos.add(unBicho);
        bichos.add(otroBicho);


        unEntrenadorAIndexar.setId(30);
        unEntrenadorAIndexar.setExperiencia(100);
        unEntrenadorAIndexar.setNivel(nivel10);
        unEntrenadorAIndexar.setUbicacion(puebloElOrigen);
        unEntrenadorAIndexar.setBichosCapturados(bichos);
        unEntrenadorAIndexar.setBilletera(100);


        otroEntrenadorAIndexar.setId(40);
        otroEntrenadorAIndexar.setExperiencia(300);
        otroEntrenadorAIndexar.setNivel(nivel10);
        otroEntrenadorAIndexar.setUbicacion(puebloElOrigen);
        otroEntrenadorAIndexar.setBichosCapturados(bichos);
        otroEntrenadorAIndexar.setBilletera(500);


        otroEntrenadorMasAIndexar.setId(50);
        otroEntrenadorMasAIndexar.setExperiencia(50);
        otroEntrenadorMasAIndexar.setNivel(nivel10);
        otroEntrenadorMasAIndexar.setUbicacion(puebloElOrigen);
        otroEntrenadorMasAIndexar.setBichosCapturados(bichos);
        otroEntrenadorMasAIndexar.setBilletera(600);

        Runner.runInSession(()-> {
        this.bootstraper.crearDatos();
        return null;});
        this.elasticSearchDAOBicho.indexar(unBicho);
        this.elasticSearchDAOSUTEntrenador.indexar(unEntrenadorAIndexar);
        this.elasticSearchDAOSUTEntrenador.indexar(otroEntrenadorAIndexar);
        this.elasticSearchDAOSUTEntrenador.indexar(otroEntrenadorMasAIndexar);

    }

   @After
   public void tearDown() throws Exception {
      this.elasticSearchDAOSUTEntrenador.deleteAll();
      this.elasticSearchDAOBicho.deleteAll();
      this.bootstraper.limpiarTabla();
    }

    @Test
    public void BuscarEntrenadoresPorExperiencia(){


       List<Entrenador> entrenadores =  busquedaServiceImplementacion.BuscarEntrenadoresPorExperiencia(50,150);

       assertEquals(entrenadores.get(0).getNombre() ,"Marcelo Tinelli");
       assertEquals(entrenadores.get(1).getNombre(),"Nestor");

    }

    @Test
    public void BuscarEntrenadoresPorNombre(){
        List<Entrenador> entrenadores = busquedaServiceImplementacion.BuscarEntrenadorPorNombre("Marcelo");
        assertEquals(entrenadores.get(0).getNombre(),"Marcelo Tinelli");
    }

    @Test
    public void BuscarPorDuenio(){
        List<Bicho> bichos = busquedaServiceImplementacion.BuscarPorDuenio("Marcelo");

        assertEquals(bichos.size(),1);
        assertEquals(bichos.get(0).getEspecie().getNombre(),"Rojomon");
    }




}


