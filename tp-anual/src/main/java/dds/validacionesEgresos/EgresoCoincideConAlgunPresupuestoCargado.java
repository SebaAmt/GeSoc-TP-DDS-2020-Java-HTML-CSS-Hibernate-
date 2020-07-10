package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.exception.EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio;
import dds.exception.EgresoNoCorrespondeConPresupuestoCargado;

import java.math.BigDecimal;

public class EgresoCoincideConAlgunPresupuestoCargado implements ValidacionEgreso{
    @Override
    public void validar(Egreso egreso){
        if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> egreso.tieneMismoProveedor(presupuesto) && egreso.tieneMismoValorTotal(presupuesto)))
            throw new EgresoNoCorrespondeConPresupuestoCargado();
    }

}
