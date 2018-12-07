package ar.edu.unq.epers.test.bichomon.dao.Elastic;


import ar.edu.unq.epers.bichomon.backend.dao.elastic.ElasticSearchDAOBicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
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

import static org.junit.Assert.*;

public class ElasticSearchDAOBichoTest
{
    private ElasticSearchDAOBicho elasticSearchDAOSUTBicho;
    private Bicho unBichoAIndexar;

    @Before
    public void setUp() throws Exception
    {
        this.elasticSearchDAOSUTBicho = new ElasticSearchDAOBicho();

        Entrenador pepe         = new Entrenador();
        pepe.setNombre("Pepe Empepado Super Fiesta");

        Especie unaEspecie      = new Especie();
        unaEspecie.setNombre("Rojomon");

        unBichoAIndexar         = new Bicho();
        unBichoAIndexar.setId(33);
        unBichoAIndexar.setVictorias(3);
        unBichoAIndexar.setDuenio(pepe);
        unBichoAIndexar.setEspecie(unaEspecie);
        unBichoAIndexar.setEnergia(1500);
        unBichoAIndexar.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1998,12,11,23,22,2)));
    }

    @After
    public void tearDown() throws Exception
    {   this.elasticSearchDAOSUTBicho.deleteAll();   }

    @Test
    public void IndexarBicho()
    {
        //Setup(Given)
        //Exercise(When)
        IndexResponse respuesta = this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar);

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("CREATED", respuesta.getResult().name());
    }

    @Test
    public void siBorroUnBichoIndexadoEsteSeBorra()
    {
        //Setup(Given)
        IndexResponse indexacion = this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar);

        //Exercise(When)
        DeleteResponse respuesta = this.elasticSearchDAOSUTBicho.borrar(indexacion.getId());

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("DELETED", respuesta.getResult().name());
    }

    @Test
    public void siBorroUnBichoNoIndexadoMeDiceQueNoEsta()
    {
        //Setup(Given)
        //Exercise(When)
        DeleteResponse respuesta = this.elasticSearchDAOSUTBicho.borrar("22");

        assertEquals(1, respuesta.getShardInfo().getSuccessful());
        assertEquals("NOT_FOUND", respuesta.getResult().name());
    }

    @Test
    public void siRecuperoUnBichoMeDevuelveElJson()
    {
        //Setup(Given)
        IndexResponse indexacion= this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar);

        //Exercise(When)
        GetResponse respuesta   = this.elasticSearchDAOSUTBicho.get(indexacion.getId());

        assertEquals(33,respuesta.getSource().get("modelId"));
        assertEquals("Rojomon", respuesta.getSource().get("especie"));
        assertEquals(1500, respuesta.getSource().get("energia"));
        assertEquals("Pepe Empepado Super Fiesta", respuesta.getSource().get("duenio"));
        assertEquals(3, respuesta.getSource().get("victorias"));
        assertEquals("1998-12-11 23:22:02.0", respuesta.getSource().get("fechaDeCaptura"));
        assertTrue(((ArrayList) respuesta.getSource().get("entrenadoresAntiguos")).isEmpty());
    }

    @Test
    public void siBuscoPorElDuenioQueContengaNombrePepeMeDaDosResultados()
    {
        //Setup(Given)
        this.prepararIndexParaBusqueda();
        //Exercise(When)
        SearchResponse unaRespuesta = this.elasticSearchDAOSUTBicho.buscarPorDuenio("Pepe");
        //Test(Then)
        assertEquals(2, unaRespuesta.getHits().totalHits);
        assertEquals("Pepe Locura", unaRespuesta.getHits().getAt(0).getSourceAsMap().get("duenio"));
        assertEquals("Pepe Empepado Super Fiesta", unaRespuesta.getHits().getAt(1).getSourceAsMap().get("duenio"));
    }

    @Test
    public void siBuscoLosTresBichosConMasVictoriasMeLosTrae()
    {
        //Setup(Given)
        this.prepararIndexParaBusqueda();
        //Exercise(When)
        SearchResponse respuesta = this.elasticSearchDAOSUTBicho.topTres();

        assertEquals(500 , respuesta.getHits().getAt(0).getSourceAsMap().get("victorias"));

        assertEquals(20  , respuesta.getHits().getAt(1).getSourceAsMap().get("victorias"));

        assertEquals(3   , respuesta.getHits().getAt(2).getSourceAsMap().get("victorias"));

    }




    private void prepararIndexParaBusqueda()
    {
        Entrenador pepeLocura   = new Entrenador();
        pepeLocura.setNombre("Pepe Locura");

        Especie unaEspecieRoja  = new Especie();
        unaEspecieRoja.setNombre("Rojomon");

        Bicho unBichoAIndexar2  = new Bicho();
        unBichoAIndexar2.setId(3);
        unBichoAIndexar2.setVictorias(0);
        unBichoAIndexar2.setDuenio(pepeLocura);
        unBichoAIndexar2.setEspecie(unaEspecieRoja);
        unBichoAIndexar2.setEnergia(100);
        unBichoAIndexar2.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(2010,1,30,15,2,1)));

        Entrenador norbit     = new Entrenador();
        norbit.setNombre("Norbit");

        Especie rasputia      = new Especie();
        rasputia.setNombre("Rasputia");

        Bicho unBichoAIndexar3= new Bicho();
        unBichoAIndexar3.setId(500);
        unBichoAIndexar3.setVictorias(500);
        unBichoAIndexar3.setDuenio(norbit);
        unBichoAIndexar3.setEspecie(rasputia);
        unBichoAIndexar3.setEnergia(5000);
        unBichoAIndexar3.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(2007,2,9,12,52,2)));

        Entrenador josue          = new Entrenador();
        josue.setNombre("Josue El Loco");

        Especie unaEspecieAmarilla= new Especie();
        unaEspecieAmarilla.setNombre("Amarillomon");

        Bicho unBichoAIndexar4    = new Bicho();
        unBichoAIndexar4.setId(5);
        unBichoAIndexar4.setVictorias(20);
        unBichoAIndexar4.setDuenio(josue);
        unBichoAIndexar4.setEspecie(unaEspecieAmarilla);
        unBichoAIndexar4.setEnergia(10);
        unBichoAIndexar4.setFechaDeCaptura(Timestamp.valueOf(LocalDateTime.of(1980,10,10,10,10,10)));

        this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar);
        this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar2);
        this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar3);
        this.elasticSearchDAOSUTBicho.indexar(unBichoAIndexar4);
    }
}
