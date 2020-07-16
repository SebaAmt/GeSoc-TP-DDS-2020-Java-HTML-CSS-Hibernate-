package dds.entidades;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class EntidadJuridica {

    private String razonSocial;
    private String nombreFicticio;
    private String cuit;
    private String direccionPostal;
    private String codigoInscripcionIGJ;
    private CategoriaEntidadJuridica categoria;
    List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
    List<Egreso> egresos = new ArrayList<Egreso>();

    public EntidadJuridica(String razonSocial, String nombreFicticio, String cuit, String direccionPostal, String codigoInscripcionIGJ) {
        this.razonSocial = razonSocial;
        this.nombreFicticio = nombreFicticio;
        this.cuit = cuit;
        this.direccionPostal = direccionPostal;
        this.codigoInscripcionIGJ = codigoInscripcionIGJ;
    }

    public void nuevoEgreso(Egreso egreso){
        this.egresos.add(egreso);
    }

    public void agregarEntidadBase(EntidadBase entidadBase){
        this.entidadesBase.add(entidadBase);
    }

    public BigDecimal totalEgresos(){
        return egresos.stream().map(egreso -> egreso.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Egreso> egresosParaValidar(){
        List<Egreso> egresosParaValidar = new ArrayList<>();
        egresosParaValidar.addAll(this.egresos.stream().filter(egreso -> egreso.requierePresupuestos() && (egreso.getEstado() == EstadoEgreso.PENDIENTE || egreso.getEstado() == EstadoEgreso.RECHAZADO)).collect(Collectors.toList()));
        this.entidadesBase.forEach(entidadBase -> egresosParaValidar.addAll(entidadBase.egresosParaValidar()));
        return egresosParaValidar;
    }

}
