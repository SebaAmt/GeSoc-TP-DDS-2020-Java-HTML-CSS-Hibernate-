package dds.exception;

public class ValorEgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio extends RuntimeException {
    public ValorEgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio() {
        super("El valor total del egreso no se corresponde con el del presupuesto seleccionado por criterio");
    }
}
