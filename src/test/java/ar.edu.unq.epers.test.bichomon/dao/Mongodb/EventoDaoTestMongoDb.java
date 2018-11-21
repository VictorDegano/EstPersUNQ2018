package ar.edu.unq.epers.test.bichomon.dao.Mongodb;

import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.excepcion.EventoRecuperarException;
import ar.edu.unq.epers.bichomon.backend.model.Evento.*;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;
import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
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
    public void tearDown()  {   eventoDAOSut.deleteAll();   }

    @Test
    public void  SiGuardoYRecuperoUnEventoNuevoEsteSeGuardaYRecuperaCorrectamente()
    {
        //Setup(Given)
        String fechaDeEvento = LocalDateTime.of(2018,11,23,22,25,30).toString();

        EventoDeCaptura nuevoEvento = new EventoDeCaptura();
        nuevoEvento.setEspecieBichoCapturado("Rojomon");
        nuevoEvento.setEntrenador("Pepinator");
        nuevoEvento.setUbicacion("Capturalandia");
        nuevoEvento.setFechaDeEvento(fechaDeEvento);

        Evento eventoRecuperado;

        //Exercise(When)
        eventoDAOSut.guardar(nuevoEvento);
        eventoRecuperado    = eventoDAOSut.recuperar(nuevoEvento.getId().toHexString());
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
        Evento eventoRecuperado    = eventoDAOSut.recuperar(id.toHexString());

        //Test(Then)
        assertEquals(id.toHexString(), eventoRecuperado.getId().toHexString());
    }

    @Test
    public void  SeRealizaUnArriboYSeGuarda()
    {
        //Setup(Given)
        String fechaDeEventoCaptura     = LocalDateTime.of(2018,10,23,20,0,0).toString();
        EventoDeCaptura eventoCaptura   = new EventoDeCaptura();
        eventoCaptura.setEspecieBichoCapturado("Amarillomon");
        eventoCaptura.setEntrenador("Pepinator");
        eventoCaptura.setUbicacion("Capturalandia");
        eventoCaptura.setFechaDeEvento(fechaDeEventoCaptura);

        String fechaDeEventoAbandono    = LocalDateTime.of(2018,11,10,13,10,0).toString();
        EventoDeAbandono eventoAbandono = new EventoDeAbandono();
        eventoAbandono.setBichoAbandonado("Rojomon");
        eventoAbandono.setEntrenador("Josefo");
        eventoAbandono.setUbicacion("Guarderia El Terror");
        eventoAbandono.setFechaDeEvento(fechaDeEventoAbandono);

        String fechaEventoDeCoronacion          = LocalDateTime.of(2018,11,10,22,12,0).toString();
        EventoDeCoronacion eventoDeCoronacion   = new EventoDeCoronacion();
        eventoDeCoronacion.setEntrenadorDestronado("");
        eventoDeCoronacion.setEntrenador("Josefo");
        eventoDeCoronacion.setUbicacion("Dojo Last Trial");
        eventoDeCoronacion.setFechaDeEvento(fechaEventoDeCoronacion);

        String fechaDeEventoArribo      = LocalDateTime.of(2018,10,1,15,10,0).toString();
        EventoDeArribo eventoDeArribo   = new EventoDeArribo();
        eventoDeArribo.setUbicacionPartida("Pueblo Paleta");
        eventoDeArribo.setUbicacion("Pueblo Lavanda");
        eventoDeArribo.setEntrenador("Pepinator");
        eventoDeArribo.setFechaDeEvento(fechaDeEventoArribo);

        eventoDAOSut.guardar(eventoCaptura);
        eventoDAOSut.guardar(eventoAbandono);
        eventoDAOSut.guardar(eventoDeCoronacion);
        eventoDAOSut.guardar(eventoDeArribo);
        //Exercise(When)
        List<Evento> eventosDePepinator =;
        List<Evento> eventosDeJosefo    =;
        //Test(Then)
    }
}
