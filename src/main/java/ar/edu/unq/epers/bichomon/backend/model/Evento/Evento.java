package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public abstract class Evento {

    @MongoId
    @MongoObjectId
    private String id;
    Entrenador entrenador;
    Ubicacion ubicacion;
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }


    public Evento( ) {
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
