package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.exception.ValorEgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio;
import dds.exception.ValorEgresoNoCorrespondeConPresupuestoCargado;

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
        if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> presupuesto.valorTotal().equals(valorTotalEgreso)))
            throw new ValorEgresoNoCorrespondeConPresupuestoCargado();
    }

    private void validarConCriterio(Egreso egreso){
        if(egreso.valorTotal().compareTo(egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos()).valorTotal()) != 0) // compareTo devuelve 0 cuando dos bigdecimal son iguales
            throw new ValorEgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio();
    }

}
