package dds.reglaNegocio;

import javax.persistence.DiscriminatorValue;


import dds.entidades.EntidadBase;
import dds.exception.ReglaNegocioException;


@DiscriminatorValue("B")
public class ReglaNegocioBloquearEntidadBase extends ReglaNegocio {

	public void puedeAgregarse(EntidadBase entidad) {
		throw new ReglaNegocioException("Esta entidad base no puede ser agregada");
	}
}
