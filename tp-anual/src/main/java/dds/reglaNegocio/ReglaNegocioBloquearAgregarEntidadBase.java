package dds.reglaNegocio;

import dds.entidades.EntidadJuridica;
import dds.exception.ReglaNegocioException;

public class ReglaNegocioBloquearAgregarEntidadBase implements ReglaNegocio {

	
	@Override
	public void agregarEntidadBase(EntidadJuridica entidad) {
		throw new ReglaNegocioException("No se pueden agregar entidades base");
	}
}
