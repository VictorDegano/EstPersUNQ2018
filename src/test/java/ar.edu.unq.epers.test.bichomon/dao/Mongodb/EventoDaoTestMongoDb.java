package ar.edu.unq.epers.test.bichomon.dao.Mongodb;

import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.model.Evento.Evento;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeCaptura;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.sun.xml.internal.ws.api.FeatureListValidatorAnnotation;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
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
    public void  SiGuardoUnEventoNuevoEsteSeGuardaCorrectamente()
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
        eventoRecuperado    = eventoDAOSut.getById(nuevoEvento.getId().toHexString());
        //Test(Then)
        assertEquals(nuevoEvento.getId().toHexString(), eventoRecuperado.getId().toHexString());
        assertEquals("Capturalandia", eventoRecuperado.getUbicacion());
        assertEquals("Pepinator", eventoRecuperado.getEntrenador());
        assertEquals("Rojomon", eventoRecuperado.getEspecieBichoCapturado());
        assertEquals(fechaDeEvento, eventoRecuperado.getFechaDeEvento());
    }


    @Test
    public void  SeRealizaUnArriboYSeGuarda(){

    }
}
