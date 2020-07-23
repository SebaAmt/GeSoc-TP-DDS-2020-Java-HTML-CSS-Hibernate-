package dds.comportamiento;

import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.exception.ComportamientoException;

public class ComportamientoValidarCantidadEgresos implements Comportamiento {

	private Integer maximaCantidad;
	
	public ComportamientoValidarCantidadEgresos(Integer maximaCantidad) {
		this.maximaCantidad = maximaCantidad;
	}
	
	@Override
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
			if (entidad.egresos().size() > maximaCantidad) {
				throw new ComportamientoException("La entidad ya ha superado la cantidad máxima de egresos a realizar.");
			}
	}

	
}
