package ar.edu.unq.epers.test.bichomon.dao.Mongodb;

import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.excepcion.EventoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.model.Evento.*;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class EventoDaoTestMongoDb
{
    private EventoDAOMongoDB eventoDAOSut;

    @Before
    public void setUp()
    {
        this.eventoDAOSut   = new EventoDAOMongoDB();
    }

    @After
    public void tearDown()  {   this.eventoDAOSut.deleteAll();   }

    @Test
    public void  SiGuardoYRecuperoUnEventoNuevoEsteSeGuardaYRecuperaCorrectamente()
    {
        //Setup(Given)
        LocalDateTime fechaDeEvento = LocalDateTime.of(2018,11,23,22,25,30);

        EventoDeCaptura nuevoEvento = new EventoDeCaptura();
        nuevoEvento.setEspecieBichoCapturado("Rojomon");
        nuevoEvento.setEntrenador("Pepinator");
        nuevoEvento.setUbicacion("Capturalandia");
        nuevoEvento.setFechaDeEvento(fechaDeEvento);

        Evento eventoRecuperado;

        //Exercise(When)
        this.eventoDAOSut.guardar(nuevoEvento);
        eventoRecuperado    = this.eventoDAOSut.recuperar(nuevoEvento.getId().toHexString());
        //Test(Then)
        assertEquals(nuevoEvento.getId().toHexString(), eventoRecuperado.getId().toHexString());
        assertEquals("Capturalandia", eventoRecuperado.getUbicacion());
        assertEquals("Pepinator", eventoRecuperado.getEntrenador());
        assertEquals("Rojomon", eventoRecuperado.getEspecieBichoCapturado());
        assertEquals(fechaDeEvento, eventoRecuperado.getFechaDeEvento());
    }

    @Test(expected = EventoRecuperarException.class)
    public void  SiSeIntentaRecuperarUnEventoQueNoEstaGuardadoRetornaUnaExcepcion()
    {
        //Setup(Given)
        ObjectId id = ObjectId.get();

        //Exercise(When)
        Evento eventoRecuperado    = this.eventoDAOSut.recuperar(id.toHexString());

        //Test(Then)
        assertEquals(id.toHexString(), eventoRecuperado.getId().toHexString());
    }

    @Test
    public void SiSeBuscaLosEventosCorrespondientesAPepinatorYLuegoLosDeJosefoSeConsiguen2ListasDeTamanio3y2Respectivamente()
    {
        //Setup(Given)
        LocalDateTime   fechaDeEventoCaptura= LocalDateTime.of(2018,10,23,20,0,0);
        EventoDeCaptura eventoCaptura       = new EventoDeCaptura();
        eventoCaptura.setEspecieBichoCapturado("Amarillomon");
        eventoCaptura.setEntrenador("Pepinator");
        eventoCaptura.setUbicacion("Capturalandia");
        eventoCaptura.setFechaDeEvento(fechaDeEventoCaptura);

        LocalDateTime fechaDeEventoAbandono = LocalDateTime.of(2018,11,10,13,10,0);
        EventoDeAbandono eventoAbandono     = new EventoDeAbandono();
        eventoAbandono.setBichoAbandonado("Rojomon");
        eventoAbandono.setEntrenador("Josefo");
        eventoAbandono.setUbicacion("Guarderia El Terror");
        eventoAbandono.setFechaDeEvento(fechaDeEventoAbandono);

        LocalDateTime fechaEventoDeCoronacion   = LocalDateTime.of(2018,11,10,22,12,0);
        EventoDeCoronacion eventoDeCoronacion   = new EventoDeCoronacion();
        eventoDeCoronacion.setEntrenadorDestronado("");
        eventoDeCoronacion.setEntrenador("Josefo");
        eventoDeCoronacion.setUbicacion("Dojo Last Trial");
        eventoDeCoronacion.setFechaDeEvento(fechaEventoDeCoronacion);

        LocalDateTime fechaEventoDeDescoronacion    = LocalDateTime.of(2018,11,12,23,0,0);
        EventoDeDescoronacion eventoDeDescoronacion = new EventoDeDescoronacion();
        eventoDeDescoronacion.setEntrenadorCoronado("Josefo");
        eventoDeDescoronacion.setUbicacion("Dojo Last Trial");
        eventoDeDescoronacion.setFechaDeEvento(fechaEventoDeDescoronacion);

        LocalDateTime fechaDeEventoArribo   = LocalDateTime.of(2018,10,1,15,10,0);
        EventoDeArribo eventoDeArribo       = new EventoDeArribo();
        eventoDeArribo.setUbicacionPartida("Pueblo Paleta");
        eventoDeArribo.setUbicacion("Pueblo Lavanda");
        eventoDeArribo.setEntrenador("Pepinator");
        eventoDeArribo.setFechaDeEvento(fechaDeEventoArribo);

        this.eventoDAOSut.guardar(eventoCaptura);
        this.eventoDAOSut.guardar(eventoAbandono);
        this.eventoDAOSut.guardar(eventoDeCoronacion);
        this.eventoDAOSut.guardar(eventoDeArribo);
        //Exercise(When)
        List<Evento> eventosDePepinator = this.eventoDAOSut.feedDeEntrenador("Pepinator");
        List<Evento> eventosDeJosefo    = this.eventoDAOSut.feedDeEntrenador("Josefo");
        //Test(Then)
        assertEquals(2, eventosDePepinator.size());
        assertEquals(fechaDeEventoAbandono, eventosDePepinator.get(0).getFechaDeEvento());
        assertEquals("Pueblo Lavanda", eventosDePepinator.get(0).getUbicacion());
        assertEquals("Pueblo Paleta", eventosDePepinator.get(0).getUbicacionPartida());
        assertEquals("Pepinator", eventosDePepinator.get(0).getEntrenador());
        assertEquals(fechaDeEventoCaptura, eventosDePepinator.get(1).getFechaDeEvento());
        assertEquals("Capturalandia", eventosDePepinator.get(1).getUbicacion());
        assertEquals("Amarillomon", eventosDePepinator.get(1).getEspecieBichoCapturado());
        assertEquals("Pepinator", eventosDePepinator.get(1).getEntrenador());

        assertEquals(3, eventosDeJosefo.size());
        assertEquals(fechaDeEventoArribo, eventosDeJosefo.get(0).getFechaDeEvento());
        assertEquals("Guarderia El Terror", eventosDeJosefo.get(0).getUbicacion());
        assertEquals("Rojomon", eventosDeJosefo.get(0).getBichoAbandonado());
        assertEquals("Josefo", eventosDeJosefo.get(0).getEntrenador());
        assertEquals(fechaEventoDeCoronacion, eventosDeJosefo.get(1).getFechaDeEvento());
        assertEquals("Dojo Last Trial", eventosDeJosefo.get(1).getUbicacion());
        assertEquals("", eventosDeJosefo.get(1).getEntrenadorDestronado());
        assertEquals("Josefo", eventosDeJosefo.get(1).getEntrenador());
        assertEquals(fechaEventoDeDescoronacion, eventosDeJosefo.get(2).getFechaDeEvento());
        assertEquals("Dojo Last Trial", eventosDeJosefo.get(2).getUbicacion());
        assertEquals("Pepinator", eventosDeJosefo.get(2).getEntrenador());
        assertEquals("Josefo", eventosDeJosefo.get(2).getEntrenadorCoronado());
    }
}
