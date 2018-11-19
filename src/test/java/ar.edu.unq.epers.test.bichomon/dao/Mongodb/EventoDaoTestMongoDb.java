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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
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
        Entrenador entrenador   = new Entrenador();
        entrenador.setNivel(new Nivel(1, 1, 99, 4));
        entrenador.setExperiencia(80);
        entrenador.setNombre("Pepinator");
        entrenador.setBilletera(0);
        entrenador.setId(35748);

        Especie red = new Especie();
        red.setNombre("Rojomon");
        red.setTipo(TipoBicho.FUEGO);
        red.setAltura(180);
        red.setPeso(75);
        red.setEnergiaIncial(100);
        red.setUrlFoto("/image/rojomon.jpg");
        red.setEspecieBase(red);

        Bicho bichoCapturado    = new Bicho(red,"");
        bichoCapturado.setEnergia(red.getEnergiaInicial());
        bichoCapturado.setPoder(20);
        bichoCapturado.setDuenio(entrenador);
        bichoCapturado.setId(55);

        entrenador.setBichosCapturados(Arrays.asList(bichoCapturado));

        Ubicacion ubicacionCaptura  = new Pueblo();
        ubicacionCaptura.setNombre("Capturalandia");
        ubicacionCaptura.agregarEntrenador(entrenador);
        entrenador.setUbicacion(ubicacionCaptura);

        LocalDateTime fechaDeEvento = LocalDateTime.of(2018,11,23,22,25,30);

        EventoDeCaptura nuevoEvento = new EventoDeCaptura();
        nuevoEvento.setId("22");
        nuevoEvento.setBichoCapturado(bichoCapturado);
        nuevoEvento.setEntrenador(entrenador);
        nuevoEvento.setUbicacion(ubicacionCaptura);
        nuevoEvento.setFechaDeEvento(fechaDeEvento);

        Evento eventoRecuperado;

        //Exercise(When)
        eventoDAOSut.guardar(nuevoEvento);
        eventoRecuperado    = eventoDAOSut.getById("22");
        //Test(Then)
        assertEquals("22", eventoRecuperado.getId());
        assertEquals("Capturalandia", eventoRecuperado.getUbicacion().getNombre());
        assertEquals("Pepinator", eventoRecuperado.getEntrenador().getNombre());
        assertEquals(55, eventoRecuperado.getBichoCapturado().getId());
        assertEquals(fechaDeEvento, eventoRecuperado.getFechaDeEvento());
    }


    @Test
    public void  SeRealizaUnArriboYSeGuarda(){

    }
}
