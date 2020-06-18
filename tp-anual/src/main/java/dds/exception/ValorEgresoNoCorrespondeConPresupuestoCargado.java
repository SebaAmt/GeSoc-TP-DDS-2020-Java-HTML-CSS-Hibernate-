package dds.exception;

public class ValorEgresoNoCorrespondeConPresupuestoCargado extends RuntimeException {
    public ValorEgresoNoCorrespondeConPresupuestoCargado() {
        super("El valor total del egreso no se corresponde con alguno de los presupuestos cargados para el");
    }
}
