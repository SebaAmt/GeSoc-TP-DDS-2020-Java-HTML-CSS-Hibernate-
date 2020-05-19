package dds.entidades;

import dds.egreso.Egreso;
import java.math.BigDecimal;
import java.util.*;

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
}
