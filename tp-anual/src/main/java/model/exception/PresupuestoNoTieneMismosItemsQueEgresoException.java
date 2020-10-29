package model.exception;

public class PresupuestoNoTieneMismosItemsQueEgresoException extends RuntimeException {
    public PresupuestoNoTieneMismosItemsQueEgresoException() {
        super("El presupuesto a agregar debe tener los mismos items que la compra");
    }
}
