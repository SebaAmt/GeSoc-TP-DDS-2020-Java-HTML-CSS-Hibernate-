package dds.exception;

public class EgresoNoCorrespondeConPresupuestoCargado extends RuntimeException {
    public EgresoNoCorrespondeConPresupuestoCargado() {
        super("El egreso no se corresponde con ninguno de los presupuestos cargados");
    }
}
