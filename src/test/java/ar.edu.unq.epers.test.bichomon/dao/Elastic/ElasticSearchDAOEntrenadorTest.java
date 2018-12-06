package ar.edu.unq.epers.test.bichomon.dao.Elastic;

import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElasticSearchDAOEntrenadorTest {
    private ElasticSearchDAOEntrenador elasticSearchDAOSUTEntrenador;
    private Entrenador unEntrenadorAIndexar;

    @Before
    public void setUp() throws Exception{
        this.elasticSearchDAOSUTEntrenador = new ElasticSearchDAOEntrenador();

        unEntrenadorAIndexar = new Entrenador();
        unEntrenadorAIndexar.setNombre("Marcelo Tinelli");
        Nivel nivel10 = new Nivel(10, 7000, 7999, 13);

        Especie unaEspecie      = new Especie();
        unaEspecie.setNombre("Rojomon");

        Ubicacion puebloElOrigen= new Pueblo();
        puebloElOrigen.setNombre("El Origen");

        Bicho unBicho    = new Bicho();
        unBicho.setNombre("pikachu");
        unBicho.setId(33);
        unBicho.setVictorias(3);
        unBicho.setDuenio(unEntrenadorAIndexar);
        unBicho.setEspecie(unaEspecie);
        unBicho.setEnergia(1500);
        unBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1998,12,11,23,22,2)));

        Bicho otroBicho    = new Bicho();
        otroBicho.setNombre("charmander");
        otroBicho.setId(34);
        otroBicho.setVictorias(3);
        otroBicho.setDuenio(unEntrenadorAIndexar);
        otroBicho.setEspecie(unaEspecie);
        otroBicho.setEnergia(1500);
        otroBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1992,12,11,23,22,2)));

        List<Bicho> bichos = new ArrayList<Bicho>();
        bichos.add(unBicho);
        bichos.add(otroBicho);


        unEntrenadorAIndexar.setId(30);
        unEntrenadorAIndexar.setExperiencia(9999);
        unEntrenadorAIndexar.setNivel(nivel10);
        unEntrenadorAIndexar.setUbicacion(puebloElOrigen);
        unEntrenadorAIndexar.setBichosCapturados(bichos);
        unEntrenadorAIndexar.setBilletera(100);


    }
    @After
    public void tearDown() throws Exception
    {   this.elasticSearchDAOSUTEntrenador.deleteAll();   }

    @Test
    public void IndexarEntrenador()
    {
        //Setup(Given)
        //Exercise(When)
        IndexResponse respuesta = this.elasticSearchDAOSUTEntrenador.indexar(unEntrenadorAIndexar);

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("CREATED", respuesta.getResult().name());
    }

    @Test
    public void borrarUnEntrenador(){
        //Setup(Given)
        IndexResponse indexacion = this.elasticSearchDAOSUTEntrenador.indexar(unEntrenadorAIndexar);

        //Exercise(When)
        DeleteResponse respuesta = this.elasticSearchDAOSUTEntrenador.borrar(indexacion.getId());

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("DELETED", respuesta.getResult().name());
    }

    @Test
    public void obtenerUnEntrenador(){
        IndexResponse indexacion= this.elasticSearchDAOSUTEntrenador.indexar(unEntrenadorAIndexar);
        List<String> nombreBichos = new ArrayList<String>();
        nombreBichos.add("pikachu");
        nombreBichos.add("charmander");

        //Exercise(When)
        GetResponse respuesta   = this.elasticSearchDAOSUTEntrenador.get(indexacion.getId());

        assertEquals(30,respuesta.getSource().get("modelId"));
        assertEquals("Marcelo Tinelli", respuesta.getSource().get("nombre"));
        assertEquals(9999, respuesta.getSource().get("exp"));
        assertEquals(10,respuesta.getSource().get("nivel"));
        assertEquals("El Origen", respuesta.getSource().get("ubicacion"));

        assertEquals(nombreBichos, respuesta.getSource().get("bichosCapturados"));

        assertEquals(100, respuesta.getSource().get("billetera"));


    }


    @Test
    public void buscarEntrenadorPorNombre(){

        this.elasticSearchDAOSUTEntrenador.indexar(unEntrenadorAIndexar);
        //Exercise(When)
        SearchResponse unaRespuesta = this.elasticSearchDAOSUTEntrenador.buscarPorNombre("Marcelo Tinelli");
        //Test(Then)
        assertEquals("Marcelo Tinelli", unaRespuesta.getHits().getAt(0).getSourceAsMap().get("nombre"));

    }

    @Test
    public void crearEntrenadoresAleatorios(){
        int entrenadores = 200;
        while(entrenadores != 0){
            Entrenador entrenador = new Entrenador();
            entrenador.setNombre("entrenador" + entrenadores);
            entrenador.setExperiencia( ThreadLocalRandom.current().nextInt(0, 1000));
            entrenador.setBilletera(ThreadLocalRandom.current().nextInt(0, 500));
            this.elasticSearchDAOSUTEntrenador.indexar(entrenador);
            entrenadores -= 1;
        }

        assertTrue(true);
    }



}
