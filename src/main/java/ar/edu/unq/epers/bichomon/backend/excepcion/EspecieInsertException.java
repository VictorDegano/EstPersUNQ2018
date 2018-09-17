package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EspecieInsertException extends EspecieException
{
    public EspecieInsertException(String especie)
    {   super(especie); }

    public EspecieInsertException(String especie, String mensajeAdicional)
    {   super(especie, mensajeAdicional);   }

    @Override
    public String getMessage()
    {   return "No se elimino la Especie" + this.getNombre() + ". " + this.getMensajeOpcional();  }
}