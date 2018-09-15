package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;

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
    @ManyToOne
    private Ubicacion ubicacion = null;



    public void moverse(Ubicacion unaNuevaUbicacion)
    {
        this.ubicacion.quitarEntrenador(this);
        this.ubicacion = unaNuevaUbicacion;
        unaNuevaUbicacion.agregarEntrenador(this);
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
}
