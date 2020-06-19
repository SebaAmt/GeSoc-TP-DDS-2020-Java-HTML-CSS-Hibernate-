package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.exception.EgresoNoCumpleCantidadMinimaDePresupuestos;

public class EgresoTieneCantidadMinimaDePresupuestos implements ValidacionEgreso {
    private int cantidadMinimaPresupuestos = 3;

    @Override
    public void validar(Egreso egreso) {
        if(egreso.getPresupuestos().size() < this.cantidadMinimaPresupuestos)
            throw new EgresoNoCumpleCantidadMinimaDePresupuestos();
    }
}
