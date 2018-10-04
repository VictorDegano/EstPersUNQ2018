package ar.edu.unq.epers.bichomon.backend.model.evolucion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CondicionEvolucion
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public abstract boolean cumpleCondicion(Bicho unBicho);

    public CondicionEvolucion() {}

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }
}
