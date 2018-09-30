package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
@Entity
public class Bicho {

	@Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Transient
	private String nombre; // TODO: 23/09/2018 en el tp 2 ya no se necesita que tengan nombre, hay que quitarselo
    @ManyToOne(cascade = CascadeType.ALL)
	private Especie especie;
	private int energia;
	@ManyToOne(cascade = CascadeType.ALL)
    private Entrenador duenio;

	public Bicho() {}

	public Bicho(Especie especie, String nombre) {
		this.especie = especie;
		this.nombre = nombre;
	}

	/**
	 * @return el nombre de un bicho (todos los bichos tienen
	 * nombre). Este NO es el nombre de su especie.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public int getEnergia() {
		return this.energia;
	}
	public void setEnergia(int energia) {
		this.energia = energia;
	}

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }

    public void setNombre(String nombre) {  this.nombre = nombre;   }

    public void setEspecie(Especie especie) {   this.especie = especie; }

    public Entrenador getDuenio() { return duenio;  }
    public void setDuenio(Entrenador duenio) {  this.duenio = duenio;   }

    public int atacar(Bicho contrincante)
    {
	    int dañoAHacer = this.daño();
		contrincante.setEnergia(contrincante.energia-dañoAHacer);
		return dañoAHacer;
    }

    public Especie getEvolucionBase(){

//	    Especie base=this.especie.getBase();
//	    return base;
        return null;
    }

    public int daño()
    {
        Double dmg = this.energia * (Math.random() * 1);
	    return dmg.intValue();
    }
}
