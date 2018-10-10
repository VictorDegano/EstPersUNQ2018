package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

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
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichosCapturados = new ArrayList<>();

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
        if(this.bichosCapturados.contains(bichoAAbandonar)){
            this.getUbicacion().refugiar(bichoAAbandonar);
        }
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


/*---------------Duelos-----------------*/

    public void retar(Bicho bichoDeCombate) {this.ubicacion.duelo(bichoDeCombate); }
}

































