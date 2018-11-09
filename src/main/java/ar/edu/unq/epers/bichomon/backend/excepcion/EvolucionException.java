package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EvolucionException extends RuntimeException
{
    private String entrenador;

    public EvolucionException(String entrenador)
    {   this.entrenador = entrenador;    }

    @Override
    public String getMessage()
    {   return "Fallo en la evolucion. El bichomon del entrenador " + this.entrenador + " no puede evolucionar";  }
}
