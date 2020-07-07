package dds;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.validacionesEgresos.ValidacionEgreso;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private String nombre;
    private List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
    private List<EntidadJuridica> entidadesJuridicas = new ArrayList<EntidadJuridica>();
    private List<ValidacionEgreso> validacionesEgresos = new ArrayList<>();

    public Organizacion(String nombre) {
        this.nombre = nombre;
    }

    public void agregarEntidadBase(EntidadBase nuevaEntidadBase){
        this.entidadesBase.add(nuevaEntidadBase);
    }

    public void agregarEntidadJuridica(EntidadJuridica nuevaEntidadJuridica){
        this.entidadesJuridicas.add(nuevaEntidadJuridica);
    }

    public void agregarValidacionEgreso(ValidacionEgreso nuevaValidacion){
        this.validacionesEgresos.add(nuevaValidacion);
    }

    public void validarEgresos() {

        List<Egreso> egresosParaValidar = new ArrayList<>();
        this.entidadesBase.stream().forEach(entidad -> egresosParaValidar.addAll(entidad.egresosParaValidar()));
        this.entidadesJuridicas.stream().forEach(entidad -> egresosParaValidar.addAll(entidad.egresosParaValidar()));

        for (Egreso egresoPendiente : egresosParaValidar) {
            try {
                this.validacionesEgresos.stream().forEach(validacion -> validacion.validar(egresoPendiente));
                egresoPendiente.setEstado(EstadoEgreso.ACEPTADO);
                egresoPendiente.getRevisor().nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue ACEPTADO");
            } catch (RuntimeException ex) {
                egresoPendiente.setEstado(EstadoEgreso.RECHAZADO);
                egresoPendiente.getRevisor()
                        .nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: " + ex.getMessage());
            }
        }
    }
    
}
