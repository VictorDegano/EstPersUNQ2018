package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dojo extends Ubicacion
{
    @OneToOne(cascade = CascadeType.ALL)
    private Campeon campeonActual;
    @OneToMany(cascade = CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
    public List<Registro> historial = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    public List<Campeon> campeonesHistoricos = new ArrayList<>();

    public Bicho campeonActual()
    {
        if (this.getCampeonActual() == null)
        {   return null;    }
        return this.campeonActual.getBichoCampeon();
    }

/*[--------]Constructors[--------]*/
    public Dojo() {}

/*[--------]Getters & Setters[--------]*/
    public Campeon getCampeonActual() { return campeonActual;   }
    public void setCampeonActual(Campeon campeonActual) {   this.campeonActual = campeonActual; }

    public List<Registro> getHistorial() {
       return this.historial;
    }

    private void agregarAHistorialDeCampeones(Campeon campeon) {    this.getHistorialDeCampeones().add(campeon); }
    public void setHistorial(List<Registro> historial) {    this.historial = historial; }

    public List<Campeon> getHistorialDeCampeones() {    return this.campeonesHistoricos;    }
    public void setHistorialDeCampeones(List<Campeon> historialDeCampeones) {   this.campeonesHistoricos = historialDeCampeones;    }

/*------------Duelos--------------*/

    private void coronarANuevoCampeon(Bicho ganador) {
        Campeon nuevoCampeon = new Campeon();
        if ( this.campeonActual != null){
            this.campeonActual.setFechaFinDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
            this.campeonesHistoricos.add(this.campeonActual);
            this.agregarAHistorialDeCampeones(this.campeonActual);
        }
        nuevoCampeon.setBichoCampeon(ganador);
        nuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
        this.campeonActual=nuevoCampeon;
    }

    public Registro duelo(Bicho bichoRetador) {
        Registro registroDeLucha = new Registro();

        // si hay un campeon comienza un duelo
        if(campeonActual != null) {

            int contadorDeTurno = 0;
            int energiaInicialCampeon = campeonActual.getBichoCampeon().getEnergia();
            int energiaInicialRetador = bichoRetador.getEnergia();
            Bicho campeon = this.campeonActual.getBichoCampeon();

            //inicia el duelo
            while (campeon.getEnergia() > 0 && bichoRetador.getEnergia() > 0 && contadorDeTurno != 10) {

                registroDeLucha.agregarComentario(new Turno((bichoRetador.getNombre() + "Ataca"), bichoRetador.atacar(campeon)));
                registroDeLucha.agregarComentario(new Turno((campeon.getNombre() + "Ataca"), campeon.atacar(bichoRetador)));
                contadorDeTurno++;
            }

            // se corona al nuevo campeon
            if (campeon.getEnergia() <= 0) {
                registroDeLucha.setGanador(bichoRetador);
                this.coronarANuevoCampeon(registroDeLucha.getGanador());
            }
            else{
                registroDeLucha.setGanador(campeon);}

            // se restaura la vida de los bichos
            Double randVal= Math.random() * 5;
            campeon.setEnergia(energiaInicialCampeon + randVal.intValue());
            bichoRetador.setEnergia(energiaInicialRetador + randVal.intValue());

        }

        // si no hay campeon, es coronado el retador
        else{
            coronarANuevoCampeon(bichoRetador);
            registroDeLucha.setGanador(bichoRetador);
        }

        // se agrega el combate al historial de combates
        historial.add(registroDeLucha);
        return registroDeLucha;
    }

    public Bicho buscar(Entrenador entrenador){
        Bicho premio = new Bicho();
        if (entrenador.getNivel().getNroDeNivel()* (Math.random()*1) >0.5)
        {
            premio= new Bicho(campeonActual.getBichoCampeon().getEvolucionBase(),"");
        }
        return premio;
    }

}
