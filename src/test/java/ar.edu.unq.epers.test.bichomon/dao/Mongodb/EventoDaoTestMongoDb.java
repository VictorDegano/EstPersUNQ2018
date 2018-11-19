package ar.edu.unq.epers.test.bichomon.dao.Mongodb;

import ar.edu.unq.epers.bichomon.backend.dao.EventoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.mongoDB.EventoDAOMongoDB;
import ar.edu.unq.epers.bichomon.backend.model.Evento.EventoDeCaptura;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import org.junit.Before;
import org.junit.Test;

public class EventoDaoTestMongoDb
{
    private EventoDAOMongoDB eventoDAOSut;

    @Before
    public void setUp()
    {
        this.eventoDAOSut   = new EventoDAOMongoDB();
    }

    @Test
    public void  SiGuardoYRecuperoUnEventoNuevoEsteSeGuardaYRecuperaCorrectamente()
    {
        //Setup(Given)
        Entrenador  entrenador      = new Entrenador();
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

        EventoDeCaptura nuevoEvento = new EventoDeCaptura();
        //Exercise(When)
        //Test(Then)
    }


    @Test
    public void  SeRealizaUnArriboYSeGuarda(){

    }
}
