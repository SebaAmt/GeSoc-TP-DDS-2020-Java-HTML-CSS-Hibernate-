package dds.comportamiento;

import dds.egreso.Egreso;
import dds.entidades.Entidad;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;

public interface Comportamiento {
	
	public default void nuevoEgreso(Entidad entidad, Egreso egreso) {}
	public default void agregarEntidadBase(EntidadJuridica entidad) {}
	public default void puedeAgregarse(EntidadBase entidad) {}
}
