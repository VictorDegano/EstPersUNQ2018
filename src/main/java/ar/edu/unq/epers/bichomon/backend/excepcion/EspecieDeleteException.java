package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EspecieDeleteException extends EspecieException
{
    public EspecieDeleteException(String especie)
    {   super(especie); }

    public EspecieDeleteException(String especie, String mensajeAdicional)
    {   super(especie, mensajeAdicional);   }

    @Override
    public String getMessage()
    {   return "No se pudo borrar la Especie" + this.getNombre() + ". " + this.getMensajeOpcional();  }
}