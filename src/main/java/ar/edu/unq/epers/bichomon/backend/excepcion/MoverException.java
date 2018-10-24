package ar.edu.unq.epers.bichomon.backend.excepcion;

public class MoverException extends RuntimeException
{
    private final String entrenador;
    private final String ubicacion;
    private final String mensajeOpcional;

    public MoverException(String entrenador, String ubicacion)
    {
        this.entrenador     = entrenador;
        this.ubicacion      = ubicacion;
        this.mensajeOpcional= "";
    }

    public MoverException(String entrenador, String ubicacion, String mensajeAdicional)
    {
        this.entrenador     = entrenador;
        this.ubicacion      = ubicacion;
        this.mensajeOpcional= mensajeAdicional;
    }

    @Override
    public String getMessage()
    {   return "Nombre de entrenador: " + entrenador + " o ubicacion: "+ ubicacion +" incorrectos. " + this.mensajeOpcional;  }

    public String getEntrenador() { return entrenador;  }
    public String getMensajeOpcional() {    return mensajeOpcional; }
}
