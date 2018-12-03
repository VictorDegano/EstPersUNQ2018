package ar.edu.unq.epers.bichomon.backend.model.Evento;

import ar.edu.unq.epers.bichomon.backend.model.Serializador.LocalDateTimeDeserializer;
import ar.edu.unq.epers.bichomon.backend.model.Serializador.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fechaDeEvento;

    public Evento() {  }

    public ObjectId getId() {   return id;  }

    public void setId(ObjectId id) {    this.id = id;   }

    public LocalDateTime getFechaDeEvento() {    return fechaDeEvento;   }

    public void setFechaDeEvento(LocalDateTime fechaDeEvento) {  this.fechaDeEvento = fechaDeEvento; }

    public String getEntrenador() { return entrenador;  }

    public void setEntrenador(String entrenador) {  this.entrenador = entrenador;   }

    public String getUbicacion() {  return ubicacion;   }

    public void setUbicacion(String ubicacion) {    this.ubicacion = ubicacion; }




    public String getEspecieBichoCapturado(){   return "";  }
    public String getUbicacionPartida()     {   return "";  }
    public String getBichoAbandonado()      {   return "";  }
    public String getEntrenadorDestronado() {   return "";  }
    public String getEntrenadorCoronado()   {   return "";  }
}
