package dds.reglaNegocio;

import javax.persistence.DiscriminatorValue;



import dds.entidades.EntidadJuridica;
import dds.exception.ReglaNegocioException;


@DiscriminatorValue("A")
public class ReglaNegocioBloquearAgregarEntidadBase extends ReglaNegocio {

	
	@Override
	public void agregarEntidadBase(EntidadJuridica entidad) {
		throw new ReglaNegocioException("No se pueden agregar entidades base");
	}
}
