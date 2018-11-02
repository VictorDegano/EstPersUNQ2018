package ar.edu.unq.epers.bichomon.backend.model.camino;

public class Camino
{
    private String desdeUbicacion;
    private String hastaUbicacion;
    private TipoUbicacion tipo;
    private int costo;

    public Camino(String desdeUbicacion, String hastaUbicacion, String tipo, int costo) {
        this.desdeUbicacion = desdeUbicacion;
        this.hastaUbicacion = hastaUbicacion;
        this.tipo           = TipoUbicacion.valueOf(tipo);
        this.costo          = costo;
    }
}
