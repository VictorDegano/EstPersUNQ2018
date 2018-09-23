package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// TODO: 14/09/2018 Plantear si nivel, ubicacion y experiencia pueden ser null
@Entity
public class Entrenador
{
    @Id @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private int experiencia;
    private int nivel;
    @ManyToOne(cascade = CascadeType.ALL)
    private Ubicacion ubicacion = null;

    //@Transient // TODO: 23/09/2018 Ignorado, cuando se vaya a trabajar sobre la lista de bichomones hay que definirle la relacion @OneToMany
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
     * Abandona el bicho con la id idBicho en la ubicacion actual. La ubicacion debe ser una
     * guarderia, si el bicho no esta en posecion del entrenador sucede nada.
     * @param idBichoAAbandonar - El id del bicho a abandonar.
     */
    public void abandonarBicho(Bicho bichoAAbandonar)
    {
//        Bicho bichoAAbandonar   = this.getBichosCapturados().stream().filter(a -> a.getId() == idBichoAAbandonar).findFirst().orElseGet(null);
//        if (bichoAAbandonar != null)
//        {
//            this.getUbicacion().abandonar(bichoAAbandonar);
//            this.getBichosCapturados().remove(bichoAAbandonar);
//        }
        this.getUbicacion().abandonar(bichoAAbandonar);
        this.getBichosCapturados().remove(bichoAAbandonar);
    }
/*[--------]Constructors[--------]*/
    public Entrenador() {   }

/*[--------]Getters & Setters[--------]*/
    public String getNombre() { return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;    }

    public int getExperiencia() {   return experiencia; }
    public void setExperiencia(int experiencia) {   this.experiencia = experiencia; }

    public int getNivel() { return nivel;   }
    public void setNivel(int nivel) {   this.nivel = nivel; }

    public Ubicacion getUbicacion() {   return ubicacion;   }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }

    public List<Bicho> getBichosCapturados() {  return bichosCapturados;    }
    public void setBichosCapturados(List<Bicho> bichosCapturados) { this.bichosCapturados = bichosCapturados;   }
}
