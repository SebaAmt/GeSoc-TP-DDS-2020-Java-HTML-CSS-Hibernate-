package dds.exception;

public class PresupuestoNoTieneMismaMoneda extends RuntimeException {
	 public PresupuestoNoTieneMismaMoneda() {
	        super("El presupuesto a agregar debe tener los misma moneda que la compra");
	    }
}
