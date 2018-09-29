package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dojo extends Ubicacion
{
    @OneToOne(cascade = CascadeType.ALL)
    private Campeon campeonActual;
    @Transient
    public List<Registro> historial= new ArrayList<>();

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
    /*------------Duelos--------------*/

    private void coronar(Bicho ganador) {
        Campeon nuevoCampeon=new Campeon();
        if ( this.campeonActual != null)
        {   this.campeonActual.setFechaFinDeCampeon(Timestamp.valueOf(LocalDateTime.now()));    }
        nuevoCampeon.setBichoCampeon(ganador);
        nuevoCampeon.setFechaInicioDeCampeon(Timestamp.valueOf(LocalDateTime.now()));
        this.campeonActual=nuevoCampeon;
    }

    public void duelo(Bicho bichoRetador) {
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
                this.coronar(registroDeLucha.ganador);

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
    }

    public Bicho buscar(Entrenador entrenador){
        Bicho premio = new Bicho();
        if (entrenador.getNivel().getNroDeNivel()* (Math.random()*1) >0.5)
        {
            premio= new Bicho(campeonActual.getBichoCampeon().getEvolucionBase(),"");
        }

    }

}
