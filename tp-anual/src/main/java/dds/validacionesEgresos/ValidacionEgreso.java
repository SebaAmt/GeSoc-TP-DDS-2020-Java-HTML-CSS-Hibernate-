package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.Presupuesto;
import dds.exception.ValidacionEgresoFallidaException;

public enum ValidacionEgreso {

    CANTIDAD_MINIMA{
        private int cantidadMinimaPresupuestos = 3; // configurable
        @Override
        public void validar(Egreso egreso) {
            if(egreso.getPresupuestos().size() < this.cantidadMinimaPresupuestos)
                throw new ValidacionEgresoFallidaException("El egreso no cumple con la cantidad minima de presupuestos cargados");
        }
    },
    COINCIDE_CON_CRITERIO{
        @Override
        public void validar(Egreso egreso) {
            if(egreso.getCriterio() == null)
                return;
            Presupuesto presupuestoPorCriterio = egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos());
            if(!(egreso.tieneMismoProveedor(presupuestoPorCriterio) && egreso.tieneMismoValorTotal(presupuestoPorCriterio)))
                throw new ValidacionEgresoFallidaException("El egreso no se corresponde con el presupuesto seleccionado por criterio");
        }
    },
    COINCIDE_CON_PRESUPUESTO_CARGADO{
        @Override
        public void validar(Egreso egreso){
            if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> egreso.tieneMismoProveedor(presupuesto) && egreso.tieneMismoValorTotal(presupuesto)))
                throw new ValidacionEgresoFallidaException("El egreso no se corresponde con ninguno de los presupuestos cargados");
        }
    };

    public abstract void validar(Egreso egreso);
}

