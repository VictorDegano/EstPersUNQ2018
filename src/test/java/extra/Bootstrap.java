package extra;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEnergia;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CondicionVictoria;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class Bootstrap
{

    public void crearDatos()
    {
        /*----------[CREACION DE NIVELES]----------*/
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

        entrenador3.setUbicacion(puebloDesert);
        puebloDesert.agregarEntrenador(entrenador3);

        entrenador4.setUbicacion(puebloElOrigen);
        puebloElOrigen.agregarEntrenador(entrenador4);

        entrenador5.setUbicacion(puebloElOrigen);
        entrenador5.setUbicacion(puebloElOrigen);



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

        // Creadas para Testear

        Especie pikachu = new Especie();
        pikachu.setNombre("Pikachu");
        pikachu.setTipo(TipoBicho.ELECTRICIDAD);
        pikachu.setAltura(394);
        pikachu.setPeso(40);
        pikachu.setEnergiaIncial(300);
        pikachu.setUrlFoto("/image.pikachu.jpg");

        Especie digimon = new Especie();
        digimon.setNombre("Digimon");
        digimon.setTipo(TipoBicho.FUEGO);
        digimon.setAltura(123);
        digimon.setPeso(40);
        digimon.setEnergiaIncial(690);
        digimon.setUrlFoto("/image.digimon.jpg");

        Especie miguelmon = new Especie();
        miguelmon.setNombre("Miguelmon");
        miguelmon.setTipo(TipoBicho.CHOCOLATE);
        miguelmon.setAltura(850);
        miguelmon.setPeso(213);
        miguelmon.setEnergiaIncial(6000);
        miguelmon.setUrlFoto("/image.miguelmon.jpg");

        Especie raichu = new Especie();
        raichu.setNombre("Raichu");
        raichu.setTipo(TipoBicho.ELECTRICIDAD);
        raichu.setAltura(402);
        raichu.setPeso(60);
        raichu.setEnergiaIncial(900);
        raichu.setUrlFoto("/image/Raichu.jpg");
        raichu.setEspecieBase(pikachu);

        pikachu.setEvolucion(raichu);
        CondicionEvolucion condicion1   = new CondicionEnergia(332);
        CondicionEvolucion condicion2   = new CondicionVictoria(5);
        pikachu.setCondicionesDeEvolucion(Arrays.asList(condicion1, condicion2));

        /*----------[CREACION DE BICHOS]----------*/
        Bicho rojo = new Bicho(red,"");
        rojo.setEnergia(red.getEnergiaInicial());
        red.setCantidadBichos(1);

        Bicho fortinator= new Bicho(fortmon, "");
        fortinator.setEnergia(fortmon.getEnergiaInicial());
        fortmon.setCantidadBichos(1);

        Bicho dientudo  = new Bicho(dientemon, "");
        dientudo.setEnergia(dientemon.getEnergiaInicial());
        dientemon.setCantidadBichos(1);

        Bicho amarillon = new Bicho(amarillo,"");
        amarillon.setEnergia(amarillo.getEnergiaInicial());
        amarillo.setCantidadBichos(1);

        Bicho verde = new Bicho(green,"");
        verde.setEnergia(green.getEnergiaInicial());
        green.setCantidadBichos(1);

        Bicho geomon = new Bicho(tierronmon,"");
        geomon.setEnergia(tierronmon.getEnergiaInicial());
        tierronmon.setCantidadBichos(1);

        Bicho gasper = new Bicho(fantasmon,"");
        gasper.setEnergia(fantasmon.getEnergiaInicial());
        fantasmon.setCantidadBichos(1);

        Bicho miguelito = new Bicho(miguelmon,"");
        miguelito.setEnergia(miguelmon.getEnergiaInicial());
        miguelmon.setCantidadBichos(1);

        Bicho patamon = new Bicho(digimon,"");
        patamon.setEnergia(digimon.getEnergiaInicial());
        digimon.setCantidadBichos(1);

        Bicho pikara = new Bicho(pikachu,"");
        pikara.setEnergia(pikachu.getEnergiaInicial());
        pikachu.setCantidadBichos(1);

        Bicho elChupaCabras = new Bicho(vampiron,"");
        elChupaCabras.setEnergia(vampiron.getEnergiaInicial());
        vampiron.setCantidadBichos(1);



        fortinator.setDuenio(entrenador1);
        entrenador1.getBichosCapturados().add(fortinator);

        dientudo.setDuenio(entrenador2);
        entrenador2.getBichosCapturados().add(dientudo);

        rojo.setDuenio(entrenador3);
        entrenador3.getBichosCapturados().add(rojo);

        amarillon.setDuenio(entrenador4);
        entrenador4.getBichosCapturados().add(amarillon);

        verde.setDuenio(entrenador5);
        entrenador5.getBichosCapturados().add(verde);

        geomon.setDuenio(entrenador1);
        entrenador1.getBichosCapturados().add(geomon);

        gasper.setDuenio(entrenador2);
        entrenador2.getBichosCapturados().add(gasper);

        miguelito.setDuenio(entrenador2);
        entrenador2.getBichosCapturados().add(miguelito);

        patamon.setDuenio(entrenador3);
        entrenador3.getBichosCapturados().add(patamon);

        pikara.setDuenio(entrenador4);
        entrenador4.getBichosCapturados().add(pikara);

        elChupaCabras.setDuenio(entrenador5);
        entrenador5.getBichosCapturados().add(elChupaCabras);

        Session session = Runner.getCurrentSession();
        session.save(condicion1);
        session.save(condicion2);

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
        session.save(entrenador3);
        session.save(entrenador4);
        session.save(entrenador5);

        session.save(red);
        session.save(amarillo);
        session.save(green);
        session.save(tierronmon);
        session.save(fantasmon);
        session.save(vampiron);
        session.save(fortmon);
        session.save(dientemon);
        session.save(pikachu);
        session.save(digimon);
        session.save(miguelmon);
        session.save(raichu);

        session.save(fortinator);
        session.save(dientudo);
        session.save(rojo);
        session.save(amarillon);
        session.save(verde);
        session.save(geomon);
        session.save(gasper);
        session.save(miguelito);
        session.save(patamon);
        session.save(pikara);
        session.save(elChupaCabras);
    }

    public void limpiarTabla()
    {
        Runner.runInSession(()-> {
            Session session = Runner.getCurrentSession();
            List nombreDeTablas = session.createNativeQuery("SHOW TABLES;").getResultList();
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate();
            for (Object tabla : nombreDeTablas)
            {   session.createNativeQuery("TRUNCATE TABLE "+tabla).executeUpdate(); }
            session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate();
            return null;
        });
    }
}