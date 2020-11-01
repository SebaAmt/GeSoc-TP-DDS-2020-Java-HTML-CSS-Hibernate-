package model.reglaNegocio;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import model.egreso.Egreso;
import model.entidades.Entidad;
import model.exception.ReglaNegocioException;

@Entity
@DiscriminatorValue("C")
public class ReglaNegocioMontoMaximo extends ReglaNegocio {

	private BigDecimal montoMaximo;
	
	public ReglaNegocioMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}
	
	public ReglaNegocioMontoMaximo() {
	}
	
	@Override
	public void nuevoEgreso(Entidad entidad, Egreso egreso) {
			if (entidad.totalEgresos().compareTo(montoMaximo) == 1 ) {
				throw new ReglaNegocioException("La entidad ya ha superado el monto maximo de egresos a realizar");
			}
	}

	
}
