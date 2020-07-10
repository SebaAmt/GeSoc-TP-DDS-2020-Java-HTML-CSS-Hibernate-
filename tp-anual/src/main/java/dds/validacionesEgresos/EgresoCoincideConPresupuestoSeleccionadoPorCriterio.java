package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.Presupuesto;
import dds.exception.EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio;

public class EgresoCoincideConPresupuestoSeleccionadoPorCriterio implements ValidacionEgreso{
    @Override
    public void validar(Egreso egreso) {
        if(egreso.getCriterio() == null)
            return;
        Presupuesto presupuestoPorCriterio = egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos());
        if(!(egreso.tieneMismoProveedor(presupuestoPorCriterio) && egreso.tieneMismoValorTotal(presupuestoPorCriterio)))
            throw new EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio();
    }

}
