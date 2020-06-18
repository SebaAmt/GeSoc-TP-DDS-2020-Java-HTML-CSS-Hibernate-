package dds.exception;

public class PresupuestoNoTieneMismosItemsQueEgreso extends RuntimeException {
    public PresupuestoNoTieneMismosItemsQueEgreso() {
        super("El presupuesto a agregar debe tener los mismos items que la compra");
    }
}
