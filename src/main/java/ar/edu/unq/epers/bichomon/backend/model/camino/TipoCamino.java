package ar.edu.unq.epers.bichomon.backend.model.camino;

public enum TipoCamino
{
    TERRESTRE(1),
    MARITIMO(2),
    AEREO(5);

    private int costo;

    private TipoCamino(int i)
    {   this.costo  = i;    }

    public int costo()
    {   return this.costo;  }
}
