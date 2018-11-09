package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import javax.persistence.CascadeType;
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
    public void refugiar(Bicho bichoAbandonado)
    {
        bichoAbandonado.getEntrenadoresAntiguos().add(bichoAbandonado.getDuenio());
        this.bichosAbandonados.add(bichoAbandonado);
        bichoAbandonado.getDuenio().getBichosCapturados().remove(bichoAbandonado);
        bichoAbandonado.setDuenio(null);
    }

    /*[--------]Constructors[--------]*/
    public Guarderia() {   }

    /*[--------]Getters & Setters[--------]*/
    public List<Bicho> getBichosAbandonados() { return bichosAbandonados;   }
    public void setBichosAbandonados(List<Bicho> bichosAbandonados) {   this.bichosAbandonados = bichosAbandonados; }

    @Override
    public Bicho buscarBicho(Entrenador entrenador)
    {
        List<Bicho> bichosSinLosMios = sacarSiEsMio(entrenador);
        if (bichosSinLosMios.isEmpty())
        {   return null;    }
        else
        {
            int bichoElegido            = (int) (Math.random()* bichosSinLosMios.size());
            Bicho bicho = bichosAbandonados.get(bichoElegido);
            bichosAbandonados.remove(bicho);
            return bicho;
        }
    }

    private List<Bicho> sacarSiEsMio(Entrenador entrenador)
    {
        List<Bicho> bichosSinLosMios = new ArrayList<Bicho>();
        for ( Bicho bicho: bichosAbandonados)
        {
            if (bicho.getEntrenadoresAntiguos().contains(entrenador))
            {   bichosSinLosMios.add(bicho); }
        }
        return bichosSinLosMios;
    }

    @Override
    public Boolean soyGuarderia(){
        return true;
    }
}
