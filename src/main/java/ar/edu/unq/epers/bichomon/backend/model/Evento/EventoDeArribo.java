package ar.edu.unq.epers.bichomon.backend.model.Evento;


public class EventoDeArribo extends Evento
{
    private String ubicacionPartida;

    /*[--------]Constructors[--------]*/
    public EventoDeArribo(String entrenador, String ubicacionVieja, String ubicacionNueva)
    {
        this.setEntrenador(entrenador);
        this.setUbicacionPartida(ubicacionVieja);
        this.setUbicacion(ubicacionNueva);
    }

    public EventoDeArribo() {   }

    /*[--------]Getters & Setters[--------]*/
    public String getUbicacionPartida() {
        return ubicacionPartida;
    }
    public void setUbicacionPartida(String ubicacionPartida) {
        this.ubicacionPartida = ubicacionPartida;
    }


}
