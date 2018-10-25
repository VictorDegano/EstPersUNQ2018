package ar.edu.unq.epers.bichomon.backend.excepcion;

public class BichoRecuperarException extends RuntimeException
{
    private int idBicho;
    private String mensajeOpcional;

    public BichoRecuperarException(int idBicho)
    {
        this.idBicho         = idBicho;
        this.mensajeOpcional = "";
    }

    public BichoRecuperarException(int idBicho, String mensajeAdicional)
    {
        this.idBicho         = idBicho;
        this.mensajeOpcional = mensajeAdicional;
    }

    @Override
    public String getMessage()
    {   return "No se pudo recuperar el Bicho de ID: " + this.idBicho + ". " + this.mensajeOpcional;  }
}
