package dds.comportamiento;

import dds.entidades.EntidadBase;
import dds.exception.ComportamientoException;

public class ComportamientoBloquearEntidadBase implements Comportamiento {

	public void puedeAgregarse(EntidadBase entidad) {
		throw new ComportamientoException("Esta entidad base no puede ser agregada");
	}
}
