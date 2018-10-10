package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
    private List<Registro> historial= new ArrayList<>();

    @OneToMany @Transient
    private List<Campeon> HistorialDeCampeones = new ArrayList<>();

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

    private void agregarAHistorialDeCampeones(Campeon campeon) {    this.HistorialDeCampeones.add(campeon); }
    public void setHistorial(List<Registro> historial) {    this.historial = historial; }

    public List<Campeon> getHistorialDeCampeones() {    return HistorialDeCampeones;    }
    public void setHistorialDeCampeones(List<Campeon> historialDeCampeones) {   HistorialDeCampeones = historialDeCampeones;    }

/*------------Duelos--------------*/

    private void coronar(Bicho ganador) {
        Campeon nuevoCampeon=new Campeon();
        if ( this.campeonActual != null)
        {
            this.campeonActual.setFechaFinDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
            this.agregarAHistorialDeCampeones(this.campeonActual);
        }
        nuevoCampeon.setBichoCampeon(ganador);
        nuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
        this.campeonActual=nuevoCampeon;
    }

    public Registro duelo(Bicho bichoRetador) {
        Registro registroDeLucha = new Registro();
        if(campeonActual != null) {
            Double randVal= Math.random() * 5;
            int contT = 0;
            int energiaC = campeonActual.getBichoCampeon().getEnergia();
            int energiaR = bichoRetador.getEnergia();
            Bicho campeon = this.campeonActual.getBichoCampeon();

            while ((campeon.getEnergia() != 0 || bichoRetador.getEnergia() != 0) && contT != 10) {

                registroDeLucha.agregarComentario(new Turno((bichoRetador.getNombre() + "Ataca"), bichoRetador.atacar(campeon)));
                registroDeLucha.agregarComentario(new Turno((campeon.getNombre() + "Ataca"), campeon.atacar(bichoRetador)));
                contT++;
            }

            if (campeon.getEnergia() <= 0) {
                registroDeLucha.setGanador(bichoRetador);
                this.coronar(registroDeLucha.getGanador());
            }else{
                registroDeLucha.setGanador(campeon);}

            campeon.setEnergia(energiaC + randVal.intValue());
            bichoRetador.setEnergia(energiaR + randVal.intValue());
        }
        else{
            coronar(bichoRetador);
            registroDeLucha.setGanador(bichoRetador);
        }
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
