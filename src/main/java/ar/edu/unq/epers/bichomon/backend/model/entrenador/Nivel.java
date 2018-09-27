package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// TODO: 27/09/2018 Plantear si hay que contemplar el caso en que se actualice los valores de experiencia (Para actualizar a los personajes al traerlos), para modelar las modificaciones en el nivel 
@Entity
public class Nivel
{
    @Id
    private int nroDeNivel;
    private int expInicial;
    private int expLimite;
    private int limiteBichomones;
    @OneToOne
    private Nivel nivelSiguiente;

    public void ganoExperiencia(Entrenador unEntrenador)
    {
        if(this.subeDeNivel(unEntrenador))
        {   unEntrenador.setNivel(this.nivelSiguiente); }
    }

    public boolean subeDeNivel(Entrenador unEntrenador)
    {   return unEntrenador.getExperiencia() > this.getExpLimite(); }

/*[--------]Constructors[--------]*/
    public Nivel() {   }

    public Nivel(int nroNivel, int expBase, int expMax, int cantBichos)
    {
        this.setNroDeNivel(nroNivel);
        this.setExpInicial(expBase);
        this.setExpLimite(expMax);
        this.setLimiteBichomones(cantBichos);
    }

/*[--------]Getters & Setters[--------]*/
    public int getNroDeNivel() {    return nroDeNivel;  }
    public void setNroDeNivel(int nroDeNivel) { this.nroDeNivel = nroDeNivel;   }

    public int getExpInicial() {    return expInicial;  }
    public void setExpInicial(int expInicial) { this.expInicial = expInicial;   }

    public int getExpLimite() { return expLimite;   }
    public void setExpLimite(int expLimite) {   this.expLimite = expLimite; }

    public int getLimiteBichomones() {  return limiteBichomones;    }
    public void setLimiteBichomones(int limiteBichomones) { this.limiteBichomones = limiteBichomones;   }

    public Nivel getNivelSiguiente() {  return nivelSiguiente;  }
    public void setNivelSiguiente(Nivel nivelSiguiente) {   this.nivelSiguiente = nivelSiguiente;   }
}
