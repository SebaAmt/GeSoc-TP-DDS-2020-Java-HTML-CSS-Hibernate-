package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.Presupuesto;
import dds.exception.ValidacionEgresoFallidaException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("segunCriterio")
public class EgresoCoincideConPresupuestoSeleccionadoPorCriterio extends ValidacionEgreso{
    @Override
    public void validar(Egreso egreso) {
        if(egreso.getCriterio() == null)
            return;
        Presupuesto presupuestoPorCriterio = egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos());
        if(!(egreso.tieneMismoProveedor(presupuestoPorCriterio) && egreso.tieneMismoValorTotal(presupuestoPorCriterio)))
            throw new ValidacionEgresoFallidaException("El egreso no se corresponde con el presupuesto seleccionado por criterio");
    }

}
