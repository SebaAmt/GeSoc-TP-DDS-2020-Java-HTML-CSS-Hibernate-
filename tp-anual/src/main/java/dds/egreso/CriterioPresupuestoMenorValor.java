package dds.egreso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CriterioPresupuestoMenorValor implements CriterioSeleccionPresupuesto {

    @Override
    public Presupuesto seleccionarPresupuesto(List<Presupuesto> presupuestos) {
        return Collections.min(presupuestos, Comparator.comparing(presupuesto -> presupuesto.valorTotal()));
    }
}
