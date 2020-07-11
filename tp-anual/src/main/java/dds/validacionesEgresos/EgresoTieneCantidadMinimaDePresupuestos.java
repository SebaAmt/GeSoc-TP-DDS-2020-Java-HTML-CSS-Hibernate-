package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.exception.ValidacionEgresoFallidaException;

public class EgresoTieneCantidadMinimaDePresupuestos implements ValidacionEgreso {
    private int cantidadMinimaPresupuestos = 3;

    @Override
    public void validar(Egreso egreso) {
        if(egreso.getPresupuestos().size() < this.cantidadMinimaPresupuestos)
            throw new ValidacionEgresoFallidaException("El egreso no cumple con la cantidad minima de presupuestos cargados");
    }
}
