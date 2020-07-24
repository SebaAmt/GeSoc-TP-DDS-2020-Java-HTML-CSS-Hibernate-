package dds.comportamiento;

import java.math.BigDecimal;

import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.exception.ComportamientoException;

public class ComportamientoMontoMaximo implements Comportamiento {

	private BigDecimal montoMaximo;
	
	public ComportamientoMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}
	
	@Override
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
			if (entidad.totalEgresos().compareTo(montoMaximo) == 1 ) {
				throw new ComportamientoException("La entidad ya ha superado el monto maximo de egresos a realizar");
			}
	}

	
}
