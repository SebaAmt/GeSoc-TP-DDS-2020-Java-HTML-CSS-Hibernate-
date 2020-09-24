package dds.reglaNegocio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


import dds.entidades.EntidadBase;
import dds.exception.ReglaNegocioException;

@Entity
@DiscriminatorValue("B")
public class ReglaNegocioBloquearEntidadBase extends ReglaNegocio {

	public void puedeAgregarse(EntidadBase entidad) {
		throw new ReglaNegocioException("Esta entidad base no puede ser agregada");
	}
}
