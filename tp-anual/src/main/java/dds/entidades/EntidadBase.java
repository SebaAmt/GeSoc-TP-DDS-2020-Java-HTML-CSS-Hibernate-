package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.math.BigDecimal;
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
