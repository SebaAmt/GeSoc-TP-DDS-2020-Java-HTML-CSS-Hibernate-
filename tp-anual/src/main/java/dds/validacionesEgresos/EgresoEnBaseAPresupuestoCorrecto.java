package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.exception.EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio;
import dds.exception.EgresoNoCorrespondeConPresupuestoCargado;

import java.math.BigDecimal;

public class EgresoEnBaseAPresupuestoCorrecto implements ValidacionEgreso{
    @Override
    public void validar(Egreso egreso){
        if(egreso.getCriterio() == null){
            validarSinCriterio(egreso);
            return;
        }
        validarConCriterio(egreso);
    }

    private void validarSinCriterio(Egreso egreso){
        BigDecimal valorTotalEgreso = egreso.valorTotal();
        Proveedor proveedorEgreso = egreso.getProveedor();
        if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> this.tienenMismoProveedor(egreso, presupuesto) && this.tienenMismoValorTotal(egreso,presupuesto)))
            throw new EgresoNoCorrespondeConPresupuestoCargado();
    }

    private void validarConCriterio(Egreso egreso){
        Presupuesto presupuestoPorCriterio = egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos());
        if(!this.tienenMismoProveedor(egreso, presupuestoPorCriterio) && this.tienenMismoValorTotal(egreso,presupuestoPorCriterio))
            throw new EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio();
    }

    private boolean tienenMismoValorTotal(Egreso egreso, Presupuesto presupuesto){
        return egreso.valorTotal().compareTo(presupuesto.valorTotal()) == 0;
    }

    private boolean tienenMismoProveedor(Egreso egreso, Presupuesto presupuesto){
        return egreso.getProveedor() == presupuesto.getProveedor();
    }

}
