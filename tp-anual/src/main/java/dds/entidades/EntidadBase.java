package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		return this.egresos.stream().filter(egreso -> egreso.requierePresupuestos() && (egreso.getEstado() == EstadoEgreso.PENDIENTE || egreso.getEstado() == EstadoEgreso.RECHAZADO)).collect(Collectors.toList());
	}
	
	public HashMap<String, BigDecimal> generarReporteMensual(){
		HashMap<String, BigDecimal> reporte = new HashMap<>();
		List<String> etiquetasAOrdenar = this.egresos().stream().flatMap(egreso -> egreso.getEtiquetasAsignadas().stream().distinct()).collect(Collectors.toList());
				
		etiquetasAOrdenar.stream().forEach(etiqueta -> {
			BigDecimal gasto = this.egresos().stream()
				.filter(egreso -> egreso.perteneceAlMesActual() && egreso.estaEtiquetadoComo(etiqueta))
				.map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			reporte.put(etiqueta, gasto);
		});
		
		return reporte;
    }
}