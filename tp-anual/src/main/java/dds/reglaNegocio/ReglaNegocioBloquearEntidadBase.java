package dds.reglaNegocio;

import dds.entidades.EntidadBase;
import dds.exception.ReglaNegocioException;

public class ReglaNegocioBloquearEntidadBase implements ReglaNegocio {

	public void puedeAgregarse(EntidadBase entidad) {
		throw new ReglaNegocioException("Esta entidad base no puede ser agregada");
	}
}
