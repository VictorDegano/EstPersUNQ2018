package ar.edu.unq.epers.bichomon.backend.excepcion;

public class EspecieUpdateException extends EspecieException {
    public EspecieUpdateException(String nombre) {
        super(nombre);
    }

    public EspecieUpdateException(String nombre, String mensajeAdicional) {
        super(nombre, mensajeAdicional);
    }

    @Override
    public String getMessage() {
        return "No se pudo actualizar la especie" + this.getNombre() + ". " + this.getMensajeOpcional();
    }
}
