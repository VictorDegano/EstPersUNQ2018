package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Registro {
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @ManyToOne
    private Bicho ganador;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Turno> detalles = new ArrayList<>();

/*[--------]Constructors[--------]*/
    public Registro() {}

/*[--------]Getters & Setters[--------]*/
    public List<Turno> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<Turno> detalles) {
        this.detalles = detalles;
    }

    public void setGanador(Bicho ganador) {
        this.ganador = ganador;
    }
    public Bicho getGanador() { return ganador; }

    public void agregarComentario(Turno turno){this.detalles.add(turno);}

    public int getId() {    return id;  }
    public void setId(int id) { this.id = id;   }
}
