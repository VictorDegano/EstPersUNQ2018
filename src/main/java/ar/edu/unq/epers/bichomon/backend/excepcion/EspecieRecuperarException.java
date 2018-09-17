package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EspecieRecuperarException extends EspecieException
{
    public EspecieRecuperarException(String nombre)
    {   super(nombre);  }

    public EspecieRecuperarException(String nombre, String mensajeAdicional)
    {   super(nombre, mensajeAdicional);    }

    @Override
    public String getMessage()
    {   return "No se pudo recuperar la Especie" + this.getNombre() + ". " + this.getMensajeOpcional();  }
}
