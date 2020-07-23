package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.util.*;
import java.util.stream.Collectors;


public class EntidadJuridica extends Entidad{

    private String razonSocial;
    private String cuit;
    private String direccionPostal;
    private String codigoInscripcionIGJ;
    List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();

    public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, String codigoInscripcionIGJ) {
    	super(nombreFicticio);
    	this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void agregarEntidadBase(EntidadBase entidadBase){
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
}
