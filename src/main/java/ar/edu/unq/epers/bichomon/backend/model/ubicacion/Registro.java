package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import java.util.ArrayList;
import java.util.List;

public class Registro {

    public Bicho ganador;
    public List<Turno> detalles;


    public Registro(){
        List<Turno> detalles = new ArrayList<>();
    }


    public List<Turno> getDetalles(){return detalles;}

    public void setDetalles(List<Turno> detalles){this.detalles = detalles;}

    public void setGanador(Bicho ganador){this.ganador = ganador;}

    public void agregarComentario(Turno turno){this.detalles.add(turno);}


}
