package ar.edu.unq.epers.bichomon.backend.excepcion;

public class UbicacionCampeonException extends RuntimeException
{

    public UbicacionCampeonException()
    { }

    @Override
    public String getMessage()
    {   return "En esta ubicacion no existen los campeones.";  }
}
