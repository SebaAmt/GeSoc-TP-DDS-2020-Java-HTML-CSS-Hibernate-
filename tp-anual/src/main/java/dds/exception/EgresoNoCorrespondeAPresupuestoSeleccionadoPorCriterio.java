package dds.exception;

public class EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio extends RuntimeException {
    public EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio() {
        super("El egreso no se corresponde con el presupuesto seleccionado por criterio");
    }
}
