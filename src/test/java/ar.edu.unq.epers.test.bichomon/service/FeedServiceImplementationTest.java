package ar.edu.unq.epers.test.bichomon.service;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.UbicacionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.EntrenadorDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.hibernate.UbicacionDAOHibernate;
import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.dao.neo4j.UbicacionDAONEO4J;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeAbandono;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeCaptura;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeCoronacion;
import ar.edu.unq.epers.bichomon.backend.model.camino.TipoCamino;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.feed.FeedService;
import ar.edu.unq.epers.bichomon.backend.service.feed.FeedServiceImplementation;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import extra.Bootstrap;
import extra.BootstrapNeo4J;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class FeedServiceImplementationTest
{
    private Bootstrap bootstraper;
    private BootstrapNeo4J bootstraperneo4j;
    private EntrenadorDAO entrenadorDAO;
    private UbicacionDAO ubicacionDAO;
    private UbicacionDAONEO4J ubicacionDAONEO4J;
    private EventoDAO eventoDAOMongoDB;
    private FeedService feedServiceSUT;
    private Ubicacion unPueblo;
    private Ubicacion unaGuarderia;
    private Ubicacion unDojo;
    private Entrenador pepeVentosa;

    @Before
    public void setUp()
    {
        this.bootstraper        = new Bootstrap();
        this.bootstraperneo4j   = new BootstrapNeo4J();
        this.entrenadorDAO      = new EntrenadorDAOHibernate();
        this.ubicacionDAO       = new UbicacionDAOHibernate();
        this.ubicacionDAONEO4J  = new UbicacionDAONEO4J();
        this.eventoDAOMongoDB   = new EventoDAOMongoDB();

        this.feedServiceSUT     = new FeedServiceImplementation(entrenadorDAO, ubicacionDAONEO4J, eventoDAOMongoDB);

        unPueblo         = new Pueblo();
        unPueblo.setNombre("Pueblo Principal");

        unaGuarderia  = new Guarderia();
        unaGuarderia.setNombre("Guarderia");

        unDojo             = new Dojo();
        unDojo.setNombre("Dojo Final");

        pepeVentosa  = new Entrenador();
        pepeVentosa.setNombre("Pepe Ventosa");
        pepeVentosa.setUbicacion(unPueblo);
        pepeVentosa.setBilletera(1);
        unPueblo.agregarEntrenador(pepeVentosa);

        Runner.runInSession(()-> {  entrenadorDAO.guardar(pepeVentosa);
                                    ubicacionDAONEO4J.create(unaGuarderia);
                                    ubicacionDAONEO4J.create(unDojo);
                                    ubicacionDAONEO4J.create(unPueblo);
                                    return null;});
    }

    @After
    public void tearDown()
    {
        this.bootstraperneo4j.limpiarTabla();
        this.bootstraper.limpiarTabla();
        this.eventoDAOMongoDB.deleteAll();
    }

    @Test
    public void DadoUnEntrenadorEnUnaUbicacionSinConexionesYSinEventosAlPedirleElFeedDeUbicacionDevuelveUnaListaVacia()
    {
        //Setup(Given)
        Ubicacion puebloAislado = new Pueblo();
        puebloAislado.setNombre("Pueblo Aislado");

        pepeVentosa.setUbicacion(puebloAislado);

        puebloAislado.agregarEntrenador(pepeVentosa);

        Runner.runInSession(()-> {  ubicacionDAONEO4J.create(puebloAislado);
                                    return null;});
        List<Evento> eventosDeUbicacion;

        //Exercise(When)
        eventosDeUbicacion  = this.feedServiceSUT.feedUbicacion("Pepe Ventosa");

        //Test(Then)
        assertEquals(0, eventosDeUbicacion.size());

    }

    @Test
    public void DadoUnEntrenadorEnUnaUbicacionConectadaAUnaSolaUbicacionSiSePideElFeedDeUbicacioesDevuelveLosEventosDeEstasDos()
    {
        //Setup(Given)
        List<Evento> eventosDeUbicacion;
        Runner.runInSession(()-> {  ubicacionDAONEO4J.conectar(unaGuarderia.getNombre(), unPueblo.getNombre(), TipoCamino.TERRESTRE);
                                    ubicacionDAONEO4J.conectar(unDojo.getNombre(), unaGuarderia.getNombre(), TipoCamino.AEREO);
                                    return null;});

        LocalDateTime fechaDeEventoCaptura  = LocalDateTime.of(2018,10,23,20,0,0);
        EventoDeCaptura eventoCaptura       = new EventoDeCaptura();
        eventoCaptura.setEspecieBichoCapturado("Amarillomon");
        eventoCaptura.setEntrenador("Pepe Ventosa");
        eventoCaptura.setUbicacion("Pueblo Principal");
        eventoCaptura.setFechaDeEvento(fechaDeEventoCaptura);

        LocalDateTime fechaDeEventoAbandono = LocalDateTime.of(2018,11,10,13,10,0);
        EventoDeAbandono eventoAbandono     = new EventoDeAbandono();
        eventoAbandono.setBichoAbandonado("Rojomon");
        eventoAbandono.setEntrenador("Pepe Ventosa");
        eventoAbandono.setUbicacion("Guarderia");
        eventoAbandono.setFechaDeEvento(fechaDeEventoAbandono);

        eventoDAOMongoDB.guardar(eventoAbandono);
        eventoDAOMongoDB.guardar(eventoCaptura);

        //Exercise(When)
        eventosDeUbicacion  = this.feedServiceSUT.feedUbicacion("Pepe Ventosa");

        //Test(Then)
        assertEquals(2, eventosDeUbicacion.size());
    }

    @Test
    public void feedEntrenador()
    {
        //Setup(Given)
        List<Evento> eventosDeEntrenador;
        LocalDateTime   fechaDeEventoCaptura= LocalDateTime.of(2018,10,23,20,0,0);
        EventoDeCaptura eventoCaptura       = new EventoDeCaptura();
        eventoCaptura.setEspecieBichoCapturado("Amarillomon");
        eventoCaptura.setEntrenador("Pepe Ventosa");
        eventoCaptura.setUbicacion("Pueblo Principal");
        eventoCaptura.setFechaDeEvento(fechaDeEventoCaptura);

        LocalDateTime fechaDeEventoAbandono = LocalDateTime.of(2018,11,10,13,10,0);
        EventoDeAbandono eventoAbandono     = new EventoDeAbandono();
        eventoAbandono.setBichoAbandonado("Rojomon");
        eventoAbandono.setEntrenador("Josefo");
        eventoAbandono.setUbicacion("Guarderia");
        eventoAbandono.setFechaDeEvento(fechaDeEventoAbandono);

        LocalDateTime fechaEventoDeCoronacion   = LocalDateTime.of(2018,11,10,22,12,0);
        EventoDeCoronacion eventoDeCoronacion   = new EventoDeCoronacion();
        eventoDeCoronacion.setEntrenadorDestronado("Pepe Ventosa");
        eventoDeCoronacion.setEntrenador("Josefo");
        eventoDeCoronacion.setUbicacion("Dojo Final");
        eventoDeCoronacion.setFechaDeEvento(fechaEventoDeCoronacion);

        eventoDAOMongoDB.guardar(eventoAbandono);
        eventoDAOMongoDB.guardar(eventoCaptura);
        eventoDAOMongoDB.guardar(eventoDeCoronacion);

        //Exercise(When)
        eventosDeEntrenador  = this.feedServiceSUT.feedEntrenador("Pepe Ventosa");

        //Test(Then)
        assertEquals(2, eventosDeEntrenador.size());
        assertEquals(fechaEventoDeCoronacion, eventosDeEntrenador.get(0).getFechaDeEvento());
        assertEquals("Pepe Ventosa", eventosDeEntrenador.get(0).getEntrenadorDestronado());
        assertEquals(fechaDeEventoCaptura, eventosDeEntrenador.get(1).getFechaDeEvento());
        assertEquals("Pepe Ventosa", eventosDeEntrenador.get(1).getEntrenador());
    }


}