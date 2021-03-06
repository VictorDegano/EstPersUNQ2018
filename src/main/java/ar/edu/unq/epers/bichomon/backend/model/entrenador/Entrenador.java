package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.camino.Camino;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Registro;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Entrenador
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private int experiencia;
    @ManyToOne
    private Nivel nivel;
    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion = null;
    @OneToMany(cascade=CascadeType.ALL) @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bicho> bichosCapturados = new ArrayList<>();
    private int billetera;
    /**
     * Mueve al entrenador a una ubicacion
     * @param unaNuevaUbicacion - {@link Ubicacion} la nueva ubicacion a la cual se va a mover
     */
    public void moverse(Ubicacion unaNuevaUbicacion)
    {
        this.ubicacion.quitarEntrenador(this);
        this.ubicacion = unaNuevaUbicacion;
        unaNuevaUbicacion.agregarEntrenador(this);
    }

    /**
     * Abandona el bicho indicado en la ubicacion actual. La ubicacion debe ser una
     * guarderia para poder refugiar y si el bicho no esta en posecion del entrenador sucede nada.
     * @param bichoAAbandonar - El bicho a refugiar.
     */
    public void abandonarBicho(Bicho bichoAAbandonar)
    {
        if(this.bichosCapturados.contains(bichoAAbandonar))
        {   this.getUbicacion().refugiar(bichoAAbandonar);  }
    }

    /**
     * El entrenador ganara experiencia y la acumulara a la cantidad de experiencia que tiene.
     * En el caso que su experiencia supere la experiencia limite de su nivel actual, subira al nivel siguiente.
     * @param experienciaGanada - La cantidad de experiencia que ganara.
     */
    public void subirExperiencia(int experienciaGanada)
    {
        this.setExperiencia(this.getExperiencia() + experienciaGanada);
        this.getNivel().ganoExperiencia(this);
    }

    public Bicho buscarBicho(){
       Bicho bicho = getUbicacion().buscar(this);
       if (bicho != null)
       {
           getBichosCapturados().add(bicho);
           bicho.setDuenio(this);
           return bicho;
       }
       else
       {    return null;    }
    }

/*[--------]Constructors[--------]*/
    public Entrenador() {   }

/*[--------]Getters & Setters[--------]*/
    public String getNombre() { return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;    }

    public int getExperiencia() {   return experiencia; }
    public void setExperiencia(int experiencia) {   this.experiencia = experiencia; }

    public Nivel getNivel() { return nivel;   }
    public void setNivel(Nivel nivel) {   this.nivel = nivel; }

    public Ubicacion getUbicacion() {   return ubicacion;   }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }

    public List<Bicho> getBichosCapturados() {  return bichosCapturados;    }
    public void setBichosCapturados(List<Bicho> bichosCapturados) { this.bichosCapturados = bichosCapturados;   }

    public int getBilletera() { return billetera; }

    public void setBilletera(int billetera) {this.billetera = billetera;}

/*---------------Duelos-----------------*/

    public Registro retar(Bicho bichoDeCombate) {   return  this.ubicacion.duelo(bichoDeCombate); }

    public boolean puedeCostearViaje(int montoACostear)
    {   return this.getBilletera() > montoACostear; }

    public void sacarDeBilletera(int monedasAGastar)
    {   this.setBilletera(this.getBilletera() - monedasAGastar);    }
}

































