package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.excepcion.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Campeon;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacion generica de una ubicacion. Una ubicacion puede tener varios {@link Entrenador} que se encuentren merodeando en ella.
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Ubicacion
{
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
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

    /**
     * Retorna el campeon actual del Dojo
     * @return El {@link Bicho} campeon del dojo
     */
    public Bicho campeonActual()
    { return null;    }

    /**
     * Abandona el bicho especificado del entrenador en su ubicacion actual.
     * @param bicho - el bicho a refugiar
     * @throws UbicacionIncorrectaException si la ubicacion no es una guarderia.
     */
    public void refugiar(Bicho bicho)
    { throw new UbicacionIncorrectaException(this.getNombre(), "No se puede refugiar un bichomon en esta Ubicacion");  }

/*[--------]Constructors[--------]*/
    public Ubicacion() {    }

/*[--------]Getters & Setters[--------]*/
    public void setCampeonActual(Campeon campeonActual)
    {   throw new UbicacionIncorrectaException(this.getNombre(), "No se puede coronar un bichomon en esta Ubicacion"); }
    public String getNombre() { return nombre;  }
    public void setNombre(String nombre) {  this.nombre = nombre;   }

    public List<Entrenador> getEntrenadores() { return entrenadores;    }
    public void setEntrenadores(List<Entrenador> entrenadores) {    this.entrenadores = entrenadores;   }

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }
/*------------------Duelos---------------*/
    public Registro duelo(Bicho bichoDeCombate)
    { throw new UbicacionIncorrectaException(this.getNombre(), "No se puede pelear en esta Ubicacion");  }

    public Bicho buscar(){ return null;}
}
