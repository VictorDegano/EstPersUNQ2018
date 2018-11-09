package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Pueblo extends Ubicacion
{
    @OneToMany(cascade = CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
    public List<ProbabilidadDeEspecie> probabilidadDeEspeciesDelPueblo ;

    @Override
    public Bicho buscarBicho(Entrenador entrenador)
    {
        int porcentaje ;
        porcentaje  = probabilidad();
        Bicho bicho = null;
        for ( ProbabilidadDeEspecie probabilidadDeEspecie: probabilidadDeEspeciesDelPueblo)
        {
            if (probabilidadDeEspecie.posibilidad > porcentaje)
            {
                bicho   = probabilidadDeEspecie.especie.crearBicho();
                bicho.setEnergia(probabilidadDeEspecie.especie.getEnergiaInicial());
                break;
            }
            else
            {   porcentaje -= probabilidadDeEspecie.posibilidad;    }
        }
        return bicho;
    }

    public int probabilidad(){
        return (int) (Math.random()* 100);
    }

    /*[--------]Constructors[--------]*/
    public Pueblo() {  }

    /*[--------]Getters & Setters[--------]*/
    public List<ProbabilidadDeEspecie> getProbabilidadDeEspeciesDelPueblo() {
        return probabilidadDeEspeciesDelPueblo;
    }
    public void setProbabilidadDeEspeciesDelPueblo(List<ProbabilidadDeEspecie> probabilidadDeEspeciesDelPueblo)
    {   this.probabilidadDeEspeciesDelPueblo = probabilidadDeEspeciesDelPueblo; }
}
