package extra;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import javax.persistence.Query;

public class Bootstrap
{

    public void crearDatos()
    {
/*----------[CREACION DE BICHOS]----------*/
        Nivel nivel1  = new Nivel(1, 1, 99, 4);
        Nivel nivel2  = new Nivel(2, 100, 399, 5);
        Nivel nivel3  = new Nivel(3, 400, 999, 6);
        Nivel nivel4  = new Nivel(4, 1000, 1999, 7);
        Nivel nivel5  = new Nivel(5, 2000, 2999, 8);
        Nivel nivel6  = new Nivel(6, 3000, 3999, 9);
        Nivel nivel7  = new Nivel(7, 4000, 4999, 10);
        Nivel nivel8  = new Nivel(8, 5000, 5999, 11);
        Nivel nivel9  = new Nivel(9, 6000, 6999, 12);
        Nivel nivel10 = new Nivel(10, 7000, 7999, 13);
        nivel1.setNivelSiguiente(nivel2);
        nivel2.setNivelSiguiente(nivel3);
        nivel3.setNivelSiguiente(nivel4);
        nivel4.setNivelSiguiente(nivel5);
        nivel5.setNivelSiguiente(nivel6);
        nivel6.setNivelSiguiente(nivel7);
        nivel7.setNivelSiguiente(nivel8);
        nivel8.setNivelSiguiente(nivel9);
        nivel9.setNivelSiguiente(nivel10);
        nivel10.setNivelSiguiente(nivel10);

/*----------[CREACION DE UBICACION]----------*/
        Ubicacion puebloElOrigen= new Pueblo();
        puebloElOrigen.setNombre("El Origen");

        Ubicacion puebloDesert  = new Pueblo();
        puebloDesert.setNombre("Desert");

        Ubicacion dojoDesert    = new Dojo();
        dojoDesert.setNombre("Dojo Desert");

        Ubicacion laGuarderia   = new Guarderia();
        laGuarderia.setNombre("La Guarderia");

/*----------[CREACION DE ENTRENADORES]----------*/
        Entrenador entrenador1  = new Entrenador();
        entrenador1.setNombre("Pepe Pepon");
        entrenador1.setExperiencia(0);
        entrenador1.setNivel(nivel1);

        Entrenador entrenador2  = new Entrenador();
        entrenador2.setNombre("El Loquillo");
        entrenador2.setExperiencia(990);
        entrenador2.setNivel(nivel3);

        Entrenador entrenador3 = new Entrenador();
        entrenador3.setNombre("Ricardo");
        entrenador3.setExperiencia(7500);
        entrenador3.setNivel(nivel1);

        Entrenador entrenador4 = new Entrenador();
        entrenador4.setNombre("Ortigoza");
        entrenador4.setExperiencia(7500);
        entrenador4.setNivel(nivel10);

        Entrenador entrenador5 = new Entrenador();
        entrenador5.setNombre("Tinelli");
        entrenador5.setExperiencia(7500);
        entrenador5.setNivel(nivel10);

        entrenador1.setUbicacion(puebloElOrigen);
        puebloElOrigen.agregarEntrenador(entrenador1);
        entrenador2.setUbicacion(puebloDesert);
        puebloDesert.agregarEntrenador(entrenador2);

/*----------[CREACION DE ESPECIES]----------*/
        Especie red = new Especie();
        red.setNombre("Rojomon");
        red.setTipo(TipoBicho.FUEGO);
        red.setAltura(180);
        red.setPeso(75);
        red.setEnergiaIncial(100);
        red.setUrlFoto("/image/rojomon.jpg");

        Especie amarillo = new Especie();
        amarillo.setNombre("Amarillomon");
        amarillo.setTipo(TipoBicho.ELECTRICIDAD);
        amarillo.setAltura(170);
        amarillo.setPeso(69);
        amarillo.setEnergiaIncial(300);
        amarillo.setUrlFoto("/image/amarillomon.png");

        Especie green = new Especie();
        green.setNombre("Verdemon");
        green.setTipo(TipoBicho.PLANTA);
        green.setAltura(150);
        green.setPeso(55);
        green.setEnergiaIncial(5000);
        green.setUrlFoto("/image/verdemon.jpg");

        Especie tierronmon = new Especie();
        tierronmon.setNombre("Tierramon");
        tierronmon.setTipo(TipoBicho.TIERRA);
        tierronmon.setAltura(1050);
        tierronmon.setPeso(99);
        tierronmon.setEnergiaIncial(5000);
        tierronmon.setUrlFoto("/image/tierramon.jpg");

        Especie fantasmon = new Especie();
        fantasmon.setNombre("Fantasmon");
        fantasmon.setTipo(TipoBicho.AIRE);
        fantasmon.setAltura(1050);
        fantasmon.setPeso(99);
        fantasmon.setEnergiaIncial(5000);
        fantasmon.setUrlFoto("/image/fantasmon.jpg");

        Especie vampiron = new Especie();
        vampiron.setNombre("Vanpiron");
        vampiron.setTipo(TipoBicho.AIRE);
        vampiron.setAltura(1050);
        vampiron.setPeso(99);
        vampiron.setEnergiaIncial(5000);
        vampiron.setUrlFoto("/image/vampiromon.jpg");

        Especie fortmon = new Especie();
        fortmon.setNombre("Fortmon");
        fortmon.setTipo(TipoBicho.CHOCOLATE);
        fortmon.setAltura(1050);
        fortmon.setPeso(99);
        fortmon.setEnergiaIncial(5000);
        fortmon.setUrlFoto("/image/fortmon.png");

        Especie dientemon = new Especie();
        dientemon.setNombre("Dientemon");
        dientemon.setTipo(TipoBicho.AGUA);
        dientemon.setAltura(1050);
        dientemon.setPeso(99);
        dientemon.setEnergiaIncial(5000);
        dientemon.setUrlFoto("/image/dientmon.jpg");

/*----------[CREACION DE BICHOS]----------*/
        Bicho fortinator= new Bicho(fortmon, "");
        fortmon.setCantidadBichos(1);
        fortinator.setEnergia(5555);

        Bicho dientudo  = new Bicho(dientemon, "");
        dientemon.setCantidadBichos(1);
        dientudo.setEnergia(80);

        fortinator.setDuenio(entrenador1);
        entrenador1.getBichosCapturados().add(fortinator);

        Session session = Runner.getCurrentSession();
        session.save(nivel1);
        session.save(nivel2);
        session.save(nivel3);
        session.save(nivel4);
        session.save(nivel5);
        session.save(nivel6);
        session.save(nivel7);
        session.save(nivel8);
        session.save(nivel9);
        session.save(nivel10);
        session.save(puebloElOrigen);
        session.save(puebloDesert);
        session.save(dojoDesert);
        session.save(laGuarderia);
        session.save(entrenador1);
        session.save(entrenador2);
        session.save(red);
        session.save(amarillo);
        session.save(green);
        session.save(tierronmon);
        session.save(fantasmon);
        session.save(vampiron);
        session.save(fortmon);
        session.save(dientemon);
        session.save(fortinator);
        session.save(dientudo);
    }

    public void limpiarTabla()
    {
        Runner.runInSession(()-> {
            Session session = Runner.getCurrentSession();
            Query query1 = session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;");
            Query query2 = session.createNativeQuery("TRUNCATE TABLE ubicacion;");
            Query query3 = session.createNativeQuery("TRUNCATE TABLE dojo;");
            Query query4 = session.createNativeQuery("TRUNCATE TABLE pueblo;");
            Query query5 = session.createNativeQuery("TRUNCATE TABLE guarderia;");
            Query query6 = session.createNativeQuery("TRUNCATE TABLE especie;");
            Query query7 = session.createNativeQuery("TRUNCATE TABLE entrenador;");
            Query query8 = session.createNativeQuery("TRUNCATE TABLE bicho;");
            Query query9 = session.createNativeQuery("TRUNCATE TABLE bicho_sequence;");
            Query query10= session.createNativeQuery("TRUNCATE TABLE entrenador_bicho;");
            Query query11= session.createNativeQuery("TRUNCATE TABLE guarderia_bicho;");
            Query query12= session.createNativeQuery("TRUNCATE TABLE nivel;");
            Query query13= session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;");
            Query query14= session.createNativeQuery("insert into bicho_sequence(next_val) values(0);");
            query1.executeUpdate();
            query2.executeUpdate();
            query3.executeUpdate();
            query4.executeUpdate();
            query5.executeUpdate();
            query6.executeUpdate();
            query7.executeUpdate();
            query8.executeUpdate();
            query9.executeUpdate();
            query10.executeUpdate();
            query11.executeUpdate();
            query12.executeUpdate();
            query13.executeUpdate();
            query14.executeUpdate();

            return null;
        });
    }
}
