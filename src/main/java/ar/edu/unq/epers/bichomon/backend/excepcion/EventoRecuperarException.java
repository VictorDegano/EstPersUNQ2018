package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EventoRecuperarException extends RuntimeException
{

    private String id;

    public EventoRecuperarException(String aId)
    {   this.id = aId;    }

    @Override
    public String getMessage()
    {
        return "No se encontro registro para el evento con ID: " + this.id;
    }
}
