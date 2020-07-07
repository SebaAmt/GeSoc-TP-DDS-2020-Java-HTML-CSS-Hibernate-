package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class EntidadBase {
	private String nombreFicticio;
	private String descripcion;
	private ArrayList<Egreso> egresos = new ArrayList<>();

	public EntidadBase(String nombreFicticio, String descripcion){
		this.nombreFicticio = nombreFicticio;
		this.descripcion = descripcion;
	}

	public void nuevoEgreso(Egreso egreso){
        this.egresos.add(egreso);
    }

    public ArrayList<Egreso> egresos(){
    	return egresos;
    }

	public BigDecimal totalEgresos(){
		return egresos.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Egreso> egresosParaValidar(){
		return this.egresos.stream().filter(egreso -> egreso.getEstado() == EstadoEgreso.PENDIENTE || egreso.getEstado() == EstadoEgreso.RECHAZADO).collect(Collectors.toList());
	}
}
