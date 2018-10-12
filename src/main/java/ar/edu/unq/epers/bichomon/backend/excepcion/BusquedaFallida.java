package ar.edu.unq.epers.bichomon.backend.excepcion;

public class BusquedaFallida extends RuntimeException {


    private String mensajeOpcional;



    public BusquedaFallida(String mensajeAdicional)
    {
        this.mensajeOpcional = mensajeAdicional;
    }

    public BusquedaFallida( )
    {

    }

    @Override
    public String getMessage()
    {   return "Fallo la busqueda" + this.mensajeOpcional;  }
}
