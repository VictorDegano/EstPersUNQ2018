package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacion generica de una ubicacion. Una ubicacion puede tener varios {@link Entrenador} que se encuentren merodeando en ella.
 */
@Entity
public class Ubicacion
{
    @Id @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    @OneToMany(mappedBy="ubicacion", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Entrenador> entrenadores   = new ArrayList<>();

    /**
     * Agrega un entrenador a la lista de entrenadores que se encuentran merodeando en ella
     * @param unEntrenador - {@link Entrenador} el entrenador que ha llegado a la ubicacion.
     */
    public void agregarEntrenador(Entrenador unEntrenador)
    {   this.getEntrenadores().add(unEntrenador);   }

    /**
     * Quita a un entrenador de la lista de entrenadores que se encuentran merodeando en ella.
     * @param unEntrenador - {@link Entrenador} el entrenador que se retira de la ubicacion.
     */
    public void quitarEntrenador(Entrenador unEntrenador)
    {
        this.getEntrenadores().remove(unEntrenador);    //No es necesario advertir de una excepcion por que el remove(Object)
                                                        // si no tiene al elemento no hace nada
    }

    /**
     * Retorna la Cantidad de Entrenadores que se encuentran en su ubicacion.
     * @return int - el numero de entrenadores que se encuentra en el.
     */
    public int cantidadDeEntrenadores()
    {   return this.getEntrenadores().size(); }

/*[--------]Constructors[--------]*/
    public Ubicacion() {    }

/*[--------]Getters & Setters[--------]*/
    public String getNombre() { return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;   }

    public List<Entrenador> getEntrenadores() { return entrenadores;    }
    public void setEntrenadores(List<Entrenador> entrenadores) {    this.entrenadores = entrenadores;   }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }
}
