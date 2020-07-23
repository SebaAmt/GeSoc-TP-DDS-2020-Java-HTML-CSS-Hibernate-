package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.util.*;
import java.util.stream.Collectors;

public class EntidadBase extends Entidad{
	private String descripcion;

	public EntidadBase(String nombreFicticio, String descripcion){
		super(nombreFicticio);
		this.descripcion = descripcion;
	}

	@Override
	public List<Egreso> egresosParaValidar(){
		return this.egresos.stream().filter(egreso -> egreso.requierePresupuestos() && (egreso.getEstado() == EstadoEgreso.PENDIENTE || egreso.getEstado() == EstadoEgreso.RECHAZADO)).collect(Collectors.toList());
	}

	public String getDescripcion() {
		return descripcion;
	}
}
