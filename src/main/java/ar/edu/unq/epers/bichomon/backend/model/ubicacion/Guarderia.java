package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guarderia extends Ubicacion
{
    @OneToMany
    private List<Bicho> bichosAbandonados   = new ArrayList<>();

    @Override
    public void abandonar(Bicho bichoAbandonado)
    {   this.getBichosAbandonados().add(bichoAbandonado);   }

/*[--------]Constructors[--------]*/
    public Guarderia() {    }

/*[--------]Getters & Setters[--------]*/
    public List<Bicho> getBichosAbandonados() { return bichosAbandonados;   }
    public void setBichosAbandonados(List<Bicho> bichosAbandonados) {   this.bichosAbandonados = bichosAbandonados; }
}
