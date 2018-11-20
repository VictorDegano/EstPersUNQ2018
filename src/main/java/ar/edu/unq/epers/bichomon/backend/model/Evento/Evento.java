package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.time.LocalDateTime;
import java.util.Date;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS,property="_class")
public abstract class Evento {

    @MongoId
    @MongoObjectId
    private ObjectId id;
    private String entrenador;
    private String ubicacion;
    private String fechaDeEvento;

    public Evento() {  }

    public ObjectId getId() {   return id;  }

    public void setId(ObjectId id) {    this.id = id;   }

    public String getFechaDeEvento() {    return fechaDeEvento;   }

    public void setFechaDeEvento(String fechaDeEvento) {  this.fechaDeEvento = fechaDeEvento; }

    public String getEntrenador() { return entrenador;  }

    public void setEntrenador(String entrenador) {  this.entrenador = entrenador;   }

    public String getUbicacion() {  return ubicacion;   }

    public void setUbicacion(String ubicacion) {    this.ubicacion = ubicacion; }




    public String getEspecieBichoCapturado() {  return "";   }
    public void setEspecieBichoCapturado(String especieBichoCapturado) {    }
}
