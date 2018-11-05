package ar.edu.unq.epers.bichomon.backend.model.camino;

public class Camino
{
    private String desdeUbicacion;
    private String hastaUbicacion;
    private TipoCamino tipo;
    private int costo;

    public Camino(String desdeUbicacion, String hastaUbicacion, String tipo, int costo) {
        this.desdeUbicacion = desdeUbicacion;
        this.hastaUbicacion = hastaUbicacion;
        this.tipo           = TipoCamino.valueOf(tipo);
        this.costo          = costo;
    }

    public String getDesdeUbicacion() {
        return desdeUbicacion;
    }

    public void setDesdeUbicacion(String desdeUbicacion) {
        this.desdeUbicacion = desdeUbicacion;
    }

    public String getHastaUbicacion() {
        return hastaUbicacion;
    }

    public void setHastaUbicacion(String hastaUbicacion) {
        this.hastaUbicacion = hastaUbicacion;
    }

    public TipoCamino getTipo() {
        return tipo;
    }

    public void setTipo(TipoCamino tipo) {
        this.tipo = tipo;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
