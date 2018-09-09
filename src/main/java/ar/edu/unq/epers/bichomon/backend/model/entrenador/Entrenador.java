package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;

@Entity
public class Entrenador
{
    @Id
    private String nombre       = "";
    private int experiencia     = 0;
    private int nivel           = 1;

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
