package dds.comportamiento;

import dds.entidades.EntidadJuridica;
import dds.exception.ComportamientoException;

public class ComportamientoBloquearAgregarEntidadBase implements Comportamiento {

	
	@Override
	public void agregarEntidadBase(EntidadJuridica entidad) {
		throw new ComportamientoException("No se pueden agregar entidades base");
	}
}
