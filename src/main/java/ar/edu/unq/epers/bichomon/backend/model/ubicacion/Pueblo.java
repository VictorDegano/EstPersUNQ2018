package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Pueblo extends Ubicacion
{
    // la sumatoria de las posibilidades de las especie tiene que ser 100
    @OneToMany(cascade=CascadeType.ALL)
    public List<ProbabilidadDeEspecie> probabilidadDeEspeciesDelPueblo ;

    public List<ProbabilidadDeEspecie> getProbabilidadDeEspeciesDelPueblo() {
        return probabilidadDeEspeciesDelPueblo;
    }

    public void setProbabilidadDeEspeciesDelPueblo(List<ProbabilidadDeEspecie> probabilidadDeEspeciesDelPueblo) {
        this.probabilidadDeEspeciesDelPueblo = probabilidadDeEspeciesDelPueblo;
    }

    public Pueblo() {  }



    public Especie BuscarEspecie(){
        int porcentaje ;
        porcentaje = probabilidad();
        Especie especie = null;
        for ( ProbabilidadDeEspecie probabilidadDeEspecie: probabilidadDeEspeciesDelPueblo) {
            if (probabilidadDeEspecie.posibilidad > porcentaje){
                especie = probabilidadDeEspecie.especie;
                break;

            }

            else{
                porcentaje -= probabilidadDeEspecie.posibilidad;
            }
        }

        return especie;

    }

    public int probabilidad(){
        return (int) Math.random()* 100;
    }


}
