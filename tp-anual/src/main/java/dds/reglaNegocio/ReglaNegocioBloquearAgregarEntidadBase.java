package dds.reglaNegocio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


import dds.entidades.EntidadJuridica;
import dds.exception.ReglaNegocioException;

@Entity
@DiscriminatorValue("A")
public class ReglaNegocioBloquearAgregarEntidadBase extends ReglaNegocio {

	
	@Override
	public void agregarEntidadBase(EntidadJuridica entidad) {
		throw new ReglaNegocioException("No se pueden agregar entidades base");
	}
}
