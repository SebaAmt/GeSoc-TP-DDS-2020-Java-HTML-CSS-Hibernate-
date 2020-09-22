package dds.entidades;

import dds.categoria.TipoCategoria;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "entidades_juridicas")
public class EntidadJuridica extends Entidad{

    private String razonSocial;
    private String cuit;
    private String direccionPostal;
    private String codigoInscripcionIGJ;
    @Enumerated
    private TipoCategoria tipoCategoria;
    @OneToMany
	@JoinColumn(name = "entidad_juridica_id")
    List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();

    public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, String codigoInscripcionIGJ) {
    	super(nombreFicticio);
    	this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void agregarEntidadBase(EntidadBase entidadBase){
    	if(this.getCategoria() != null) {
    		this.getCategoria().agregarEntidadBase(this);
    		entidadBase.getCategoria().puedeAgregarse(entidadBase);
    	}
        this.entidadesBase.add(entidadBase);
    }

    @Override
    public List<Egreso> egresosParaValidar(){
        List<Egreso> egresosParaValidar = new ArrayList<>();
        egresosParaValidar.addAll(this.egresos.stream().filter(egreso -> egreso.requierePresupuestos() && (egreso.getEstado() == EstadoEgreso.PENDIENTE || egreso.getEstado() == EstadoEgreso.RECHAZADO)).collect(Collectors.toList()));
        this.entidadesBase.forEach(entidadBase -> egresosParaValidar.addAll(entidadBase.egresosParaValidar()));
        return egresosParaValidar;
    }

	public String getRazonSocial() {
		return razonSocial;
	}

	public String getCuit() {
		return cuit;
	}

	public String getDireccionPostal() {
		return direccionPostal;
	}

	public String getCodigoInscripcionIGJ() {
		return codigoInscripcionIGJ;
	}
	
	public TipoCategoria getTipoCategoria() {
		return this.tipoCategoria;
	}

    public HashMap<String, BigDecimal> generarReporteMensual(){
		HashMap<String, BigDecimal> reporte = new HashMap<>();
		List<Egreso> egresosDeEntidadesBase = this.entidadesBase.stream().flatMap(entidad -> entidad.egresos().stream()).collect(Collectors.toList());
		List<Egreso> egresosAConsiderar = egresosDeEntidadesBase;
		egresosAConsiderar.addAll(this.egresos());
		List<String> etiquetasAOrdenar = egresosAConsiderar.stream().flatMap(egreso -> egreso.getEtiquetasAsignadas().stream()).distinct().collect(Collectors.toList());
		
		etiquetasAOrdenar.stream().forEach(etiqueta -> {
			BigDecimal gasto = egresosAConsiderar.stream()
				.filter(egreso -> egreso.perteneceAlMesActual() && egreso.estaEtiquetadoComo(etiqueta))
				.map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			reporte.put(etiqueta, gasto);
		});
		
		return reporte;
    }
}
