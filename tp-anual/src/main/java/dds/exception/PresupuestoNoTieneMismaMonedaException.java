package dds.exception;

public class PresupuestoNoTieneMismaMonedaException extends RuntimeException {
	 public PresupuestoNoTieneMismaMonedaException() {
	        super("El presupuesto a agregar debe tener los misma moneda que la compra");
	    }
}


