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
    private Entrenador entrenadorUno;
    private Nivel nivel10;
    private Nivel nivel1;
    private List<Bicho> bichos;
    private Ubicacion puebloElOrigen;
    private Entrenador entrenadorDos;
    private Entrenador entrenadorTres;
    private Entrenador entrenadorCuatro;

    @Before
    public void setUp() throws Exception{
        this.elasticSearchDAOSUTEntrenador = new ElasticSearchDAOEntrenador();

        entrenadorUno = new Entrenador();
        entrenadorUno.setNombre("Marcelo Tinelli");
        nivel10 = new Nivel(10, 7000, 7999, 13);
        nivel1  = new Nivel(1, 0, 400, 0);

        entrenadorDos = new Entrenador();
        entrenadorDos.setNombre("Miguel");

        entrenadorTres = new Entrenador();
        entrenadorTres.setNombre("Nestor");

        entrenadorCuatro = new Entrenador();
        entrenadorCuatro.setNombre("Mauricio");



        Especie unaEspecie      = new Especie();
        unaEspecie.setNombre("Rojomon");

        puebloElOrigen= new Pueblo();
        puebloElOrigen.setNombre("El Origen");

        Bicho unBicho    = new Bicho();
        unBicho.setNombre("pikachu");
        unBicho.setId(33);
        unBicho.setVictorias(3);
        unBicho.setDuenio(entrenadorUno);
        unBicho.setEspecie(unaEspecie);
        unBicho.setEnergia(1500);
        unBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1998,12,11,23,22,2)));

        Bicho otroBicho    = new Bicho();
        otroBicho.setNombre("charmander");
        otroBicho.setId(34);
        otroBicho.setVictorias(3);
        otroBicho.setDuenio(entrenadorUno);
        otroBicho.setEspecie(unaEspecie);
        otroBicho.setEnergia(1500);
        otroBicho.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1992,12,11,23,22,2)));

        bichos = new ArrayList<Bicho>();
        bichos.add(unBicho);
        bichos.add(otroBicho);


        entrenadorUno.setId(30);
        entrenadorUno.setExperiencia(100);
        entrenadorUno.setNivel(nivel10);
        entrenadorUno.setUbicacion(puebloElOrigen);
        entrenadorUno.setBichosCapturados(bichos);
        entrenadorUno.setBilletera(100);


        entrenadorDos.setId(40);
        entrenadorDos.setExperiencia(300);
        entrenadorDos.setNivel(nivel10);
        entrenadorDos.setUbicacion(puebloElOrigen);
        entrenadorDos.setBichosCapturados(bichos);
        entrenadorDos.setBilletera(500);


        entrenadorTres.setId(50);
        entrenadorTres.setExperiencia(50);
        entrenadorTres.setNivel(nivel10);
        entrenadorTres.setUbicacion(puebloElOrigen);
        entrenadorTres.setBichosCapturados(bichos);
        entrenadorTres.setBilletera(600);

        entrenadorCuatro.setId(60);
        entrenadorCuatro.setExperiencia(0);
        entrenadorCuatro.setNivel(nivel1);
        entrenadorCuatro.setUbicacion(puebloElOrigen);
        entrenadorCuatro.setBilletera(0);
    }

   @After
   public void tearDown() throws Exception
   {   this.elasticSearchDAOSUTEntrenador.deleteAll();   }

    @Test
    public void IndexarEntrenador()
    {
        //Setup(Given)
        //Exercise(When)
        IndexResponse respuesta = this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("CREATED", respuesta.getResult().name());
    }

    @Test
    public void borrarUnEntrenador(){
        //Setup(Given)
        IndexResponse indexacion = this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);

        //Exercise(When)
        DeleteResponse respuesta = this.elasticSearchDAOSUTEntrenador.borrar(indexacion.getId());

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("DELETED", respuesta.getResult().name());
    }

    @Test
    public void obtenerUnEntrenador(){
        IndexResponse indexacion= this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);
        List<String> nombreBichos = new ArrayList<String>();
        nombreBichos.add("pikachu");
        nombreBichos.add("charmander");

        //Exercise(When)
        GetResponse respuesta   = this.elasticSearchDAOSUTEntrenador.get(indexacion.getId());

        assertEquals(30,respuesta.getSource().get("modelId"));
        assertEquals("Marcelo Tinelli", respuesta.getSource().get("nombre"));
        assertEquals(100, respuesta.getSource().get("exp"));
        assertEquals(10,respuesta.getSource().get("nivel"));
        assertEquals("El Origen", respuesta.getSource().get("ubicacion"));

        assertEquals(nombreBichos, respuesta.getSource().get("bichosCapturados"));

        assertEquals(100, respuesta.getSource().get("billetera"));


    }




   /* @Test
    public void crearEntrenadoresAleatorios(){
        int entrenadores = 50;
        while(entrenadores != 0){
            Entrenador entrenador = new Entrenador();
            entrenador.setId(entrenadores);
            entrenador.setNombre("entrenador" + entrenadores);
            entrenador.setExperiencia( ThreadLocalRandom.current().nextInt(0, 1000));
            entrenador.setBilletera(ThreadLocalRandom.current().nextInt(0, 500));
            entrenador.setNivel(nivel10);
            entrenador.setBichosCapturados(bichos);
            entrenador.setUbicacion(puebloElOrigen);
            this.elasticSearchDAOSUTEntrenador.indexar(entrenador);
            entrenadores -= 1;
        }

        assertTrue(true);
    }*/

    @Test
    public void buscarEntrenadoresDesde100DeExpHasta300(){

        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);
        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorDos);
        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorTres);

        SearchResponse unaRespuesta = this.elasticSearchDAOSUTEntrenador.buscarEntrenadoresConCiertaExperiencia(100,300);

        assertEquals(unaRespuesta.getHits().getAt(0).getSourceAsMap().get("nombre"),"Marcelo Tinelli");
        assertEquals(unaRespuesta.getHits().getAt(1).getSourceAsMap().get("nombre"),"Miguel");
    }

    @Test
    public void buscarEntrenadoresPorNivel(){

        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);
        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorDos);
        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorTres);
        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorCuatro);

        SearchResponse unaRespuesta = this.elasticSearchDAOSUTEntrenador.buscarEntrenadoresDeNivel(1);

        assertEquals(unaRespuesta.getHits().getAt(0).getSourceAsMap().get("nombre"),"Mauricio");
    }



    @Test
    public void buscarEntrenadorPorNombre(){

        this.elasticSearchDAOSUTEntrenador.indexar(entrenadorUno);
        //Exercise(When)
        SearchResponse unaRespuesta = this.elasticSearchDAOSUTEntrenador.buscarPorNombre("Marcelo Tinelli");
        //Test(Then)
        assertEquals("Marcelo Tinelli", unaRespuesta.getHits().getAt(0).getSourceAsMap().get("nombre"));

    }

}
