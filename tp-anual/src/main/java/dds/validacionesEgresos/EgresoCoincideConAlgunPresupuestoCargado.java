package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.exception.ValidacionEgresoFallidaException;

public class EgresoCoincideConAlgunPresupuestoCargado implements ValidacionEgreso{
    @Override
    public void validar(Egreso egreso){
        if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> egreso.tieneMismoProveedor(presupuesto) && egreso.tieneMismoValorTotal(presupuesto)))
            throw new ValidacionEgresoFallidaException("El egreso no se corresponde con ninguno de los presupuestos cargados");
    }

}
