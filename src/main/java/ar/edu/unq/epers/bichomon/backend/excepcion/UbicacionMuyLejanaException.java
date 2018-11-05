package ar.edu.unq.epers.bichomon.backend.excepcion;

public class UbicacionMuyLejanaException  extends RuntimeException
{
    private final String ubicacion;

    public UbicacionMuyLejanaException(String nombreUbicacion)
    {   this.ubicacion  = nombreUbicacion;  }

    @Override
    public String getMessage()
    {   return "La Ubicacion " + ubicacion + " ¡Esta demasiado lejos para ir!";    }
}

