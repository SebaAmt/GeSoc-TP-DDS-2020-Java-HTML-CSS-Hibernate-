package dds.exception;

public class EgresoNoCumpleCantidadMinimaDePresupuestos extends RuntimeException {
    public EgresoNoCumpleCantidadMinimaDePresupuestos() {
        super("El egreso no cumple con la cantidad minima de presupuestos cargados");
    }
}
