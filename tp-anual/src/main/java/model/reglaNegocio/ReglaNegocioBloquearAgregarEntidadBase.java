package model.reglaNegocio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


import model.entidades.EntidadJuridica;
import model.exception.ReglaNegocioException;

@Entity
@DiscriminatorValue("A")
public class ReglaNegocioBloquearAgregarEntidadBase extends ReglaNegocio {

	
	@Override
	public void agregarEntidadBase(EntidadJuridica entidad) {
		throw new ReglaNegocioException("No se pueden agregar entidades base");
	}
}
