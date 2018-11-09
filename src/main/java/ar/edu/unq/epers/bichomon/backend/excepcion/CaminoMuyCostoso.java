package ar.edu.unq.epers.bichomon.backend.excepcion;

public class CaminoMuyCostoso extends RuntimeException {

    private String ubicacion;

    public CaminoMuyCostoso(String ubicacion)
    {   this.ubicacion  = ubicacion;    }

    @Override
    public String getMessage()
    {   return "No se puede ir a " + ubicacion + ". El camino es muy costoso.";    }
}
