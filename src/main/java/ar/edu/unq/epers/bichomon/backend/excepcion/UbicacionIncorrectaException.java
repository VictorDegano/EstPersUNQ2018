package ar.edu.unq.epers.bichomon.backend.excepcion;

public class UbicacionIncorrectaException extends RuntimeException
{
    private final String ubicacion;
    private final String mensajeOpcional;

    public UbicacionIncorrectaException(String ubicacion, String mensajeOpcional)
    {
        this.ubicacion      = ubicacion;
        this.mensajeOpcional= mensajeOpcional;
    }

    public UbicacionIncorrectaException(String ubicacion)
    {
        this.ubicacion      = ubicacion;
        this.mensajeOpcional= null;
    }

    @Override
    public String getMessage()
    {
        if (this.mensajeOpcional == null)
        {   return "La Ubicacion: " + ubicacion + " es incorrecta.";    }
        else
        {   return "La Ubicacion: " + ubicacion + " es incorrecta. " + this.mensajeOpcional;    }
    }
}
