package ar.edu.unq.epers.bichomon.backend.excepcion;


public abstract class EspecieException extends RuntimeException
{
    private final String nombre;
    private final String mensajeOpcional;

    public EspecieException(String nombre)
    {
        this.nombre         = nombre;
        this.mensajeOpcional= "";
    }

    public EspecieException(String nombre, String mensajeAdicional)
    {
        this.nombre         = nombre;
        this.mensajeOpcional= mensajeAdicional;
    }

    @Override
    abstract public String getMessage();

    public String getNombre() { return nombre;  }
    public String getMensajeOpcional() {    return mensajeOpcional; }
}
