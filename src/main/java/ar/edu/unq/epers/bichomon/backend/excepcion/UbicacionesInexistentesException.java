package ar.edu.unq.epers.bichomon.backend.excepcion;

public class UbicacionesInexistentesException extends RuntimeException
{
    private final String ubicaciones;

    public UbicacionesInexistentesException(String ubicaciones)
    {   this.ubicaciones    = ubicaciones;  }

    @Override
    public String getMessage()
    {   return "Ubicacion Inexistente, por favor revise si: " + this.ubicaciones + " existen.";    }
}
