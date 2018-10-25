package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

import javax.persistence.*;

@Entity
public class ProbabilidadDeEspecie
{
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @OneToOne
    public Especie especie;

    public int posibilidad;

    public ProbabilidadDeEspecie() { }

    public ProbabilidadDeEspecie(Especie especie,int posibilidad)
    {
        this.especie = especie;
        this.posibilidad = posibilidad;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public float getPosibilidad() {
        return posibilidad;
    }

    public void setPosibilidad(int posibilidad) {
        this.posibilidad = posibilidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
