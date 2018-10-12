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

    @OneToMany(cascade = CascadeType.ALL)
    private List<RegistroDeAbandono> registroDeBichosAbandonados = new  ArrayList<>();

    @Override
    public void refugiar(Bicho bichoAbandonado){

        RegistroDeAbandono registro = new RegistroDeAbandono(bichoAbandonado.getDuenio(),bichoAbandonado);
        this.registroDeBichosAbandonados.add(registro);
        this.bichosAbandonados.add(bichoAbandonado);
        bichoAbandonado.getDuenio().getBichosCapturados().remove(bichoAbandonado);
        bichoAbandonado.setDuenio(null);
    }

/*[--------]Constructors[--------]*/
    public Guarderia() {   }

    /*[--------]Getters & Setters[--------]*/
    public List<Bicho> getBichosAbandonados() { return bichosAbandonados;   }
    public void setBichosAbandonados(List<Bicho> bichosAbandonados) {   this.bichosAbandonados = bichosAbandonados; }

    public List<RegistroDeAbandono> getRegistroDeBichosAbandonados() {
        return registroDeBichosAbandonados;
    }

    public void setRegistroDeBichosAbandonados(List<RegistroDeAbandono> registroDeBichosAbandonados) {
        this.registroDeBichosAbandonados = registroDeBichosAbandonados;
    }

    @Override
    public Bicho buscarBicho(Entrenador entrenador){
        List<RegistroDeAbandono> registroDeAbandonosSinLosMios = sacarSiEsMio(entrenador);
        if (registroDeAbandonosSinLosMios.isEmpty())
        {
            return null;
        }

        else{
            int bichoElegido = (int) (Math.random()* registroDeAbandonosSinLosMios.size());
            RegistroDeAbandono registro = getRegistroDeBichosAbandonados().get(bichoElegido);
            getBichosAbandonados().remove(registro);
            bichosAbandonados.remove(registro.getBichomon());
            return registro.getBichomon();
        }



    }

    private List<RegistroDeAbandono> sacarSiEsMio(Entrenador entrenador){
        List<RegistroDeAbandono> registroDeBichos = new ArrayList<RegistroDeAbandono>();
        for ( RegistroDeAbandono registro: registroDeBichosAbandonados){
            if (registro.getEntrenador().getNombre() != entrenador.getNombre()){
                registroDeBichos.add(registro);
            }
        }
        return registroDeBichos;
    }

    @Override
    public Boolean soyGuarderia(){
        return true;
    }

}
