package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.util.List;

public class ValidadorEgresos {

	private List<Egreso> egresosPendientes;
	private List<ValidacionEgreso> validaciones;

	public ValidadorEgresos(List<Egreso> egresosPendientes, List<ValidacionEgreso> validaciones) {
		this.egresosPendientes = egresosPendientes;
		this.validaciones = validaciones;
	}

	public void nuevoEgresoPendiente(Egreso egreso) {
		this.egresosPendientes.add(egreso);
	}

	public void agregarValidacion(ValidacionEgreso validacion) {
		this.validaciones.add(validacion);
	}

	public void validarEgresosPendientes() {
		for (Egreso egresoPendiente : this.egresosPendientes) {
			try {
				this.validaciones.stream().forEach(validacion -> validacion.validar(egresoPendiente));
				egresoPendiente.setEstado(EstadoEgreso.ACEPTADO);
				egresoPendiente.getRevisor().nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue ACEPTADO");
			} catch (RuntimeException ex) {
				egresoPendiente.setEstado(EstadoEgreso.RECHAZADO);
				egresoPendiente.getRevisor()
						.nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: " + ex.getMessage());
			}

		}
	}

	public List<Egreso> getEgresosPendientes() {
		return this.egresosPendientes;
	}
}
