package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;

import javax.persistence.*;

@Entity
public class RegistroDeAbandono {

    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @ManyToOne
    Entrenador entrenador;

    @ManyToOne
    Bicho bichomon;

    public RegistroDeAbandono(){}

    public RegistroDeAbandono(Entrenador entrenador, Bicho bichomon ){
        this.entrenador = entrenador;
        this.bichomon = bichomon;
    }



}
