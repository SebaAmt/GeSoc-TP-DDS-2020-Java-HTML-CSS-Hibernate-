package model.egreso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum CriterioSeleccionPresupuesto {

	MENOR_VALOR {
        @Override
		public Presupuesto seleccionarPresupuesto(List<Presupuesto> presupuestos) {
			return Collections.min(presupuestos, Comparator.comparing(presupuesto -> presupuesto.valorTotal()));
		}

	};
	
	 public abstract Presupuesto seleccionarPresupuesto(List<Presupuesto> presupuestos);
}