package ar.edu.unq.epers.bichomon.backend.excepcion;

public class CaminoMuyCostoso extends RuntimeException {



    public CaminoMuyCostoso(){}

    @Override
    public String getMessage()
    {   return "El camino es muy costoso";    }
}
