package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Experiencia
{
    @Id @Enumerated
    private TipoExperiencia nombre;
    private int experiencia;

    public Experiencia() {  }

    public Experiencia(TipoExperiencia tipoDeExp, int valor)
    {
        this.nombre     = tipoDeExp;
        this.experiencia= valor;
    }

    public int getExperiencia() {   return experiencia; }
    public void setExperiencia(int experiencia) {   this.experiencia = experiencia; }

    public TipoExperiencia getNombre() {    return nombre;  }
    public void setNombre(TipoExperiencia nombre) { this.nombre = nombre;   }
}
