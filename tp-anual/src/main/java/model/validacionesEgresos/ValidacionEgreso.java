package model.validacionesEgresos;

import model.egreso.Egreso;
import model.egreso.Presupuesto;
import model.exception.ValidacionEgresoFallidaException;

public enum ValidacionEgreso {

    CANTIDAD_MINIMA("Cantidad mínima"){
        private int cantidadMinimaPresupuestos = 3; // configurable
        @Override
        public void validar(Egreso egreso) {
            if(egreso.getPresupuestos().size() < this.cantidadMinimaPresupuestos)
                throw new ValidacionEgresoFallidaException("El egreso no cumple con la cantidad minima de presupuestos cargados");
        }
    },
    COINCIDE_CON_CRITERIO("Coincide con criterio"){
        @Override
        public void validar(Egreso egreso) {
            if(egreso.getCriterio() == null)
                return;
            Presupuesto presupuestoPorCriterio = egreso.getCriterio().seleccionarPresupuesto(egreso.getPresupuestos());
            if(!(egreso.tieneMismoProveedor(presupuestoPorCriterio) && egreso.tieneMismoValorTotal(presupuestoPorCriterio)))
                throw new ValidacionEgresoFallidaException("El egreso no se corresponde con el presupuesto seleccionado por criterio");
        }
    },
    COINCIDE_CON_PRESUPUESTO_CARGADO("Coincide con presupuesto cargado"){
        @Override
        public void validar(Egreso egreso){
            if(!egreso.getPresupuestos().stream().anyMatch(presupuesto -> egreso.tieneMismoProveedor(presupuesto) && egreso.tieneMismoValorTotal(presupuesto)))
                throw new ValidacionEgresoFallidaException("El egreso no se corresponde con ninguno de los presupuestos cargados");
        }
    };

    public abstract void validar(Egreso egreso);

    private String nombre;

    ValidacionEgreso(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}

