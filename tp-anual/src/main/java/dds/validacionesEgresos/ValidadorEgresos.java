package dds.validacionesEgresos;

import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ValidadorEgresos {

    private List<Egreso> egresosPendientes = new ArrayList<>();
    private List<ValidacionEgreso> validaciones = new ArrayList<>();

    public ValidadorEgresos(List<Egreso> egresosPendientes, List<ValidacionEgreso> validaciones) {
        this.egresosPendientes = egresosPendientes;
        this.validaciones = validaciones;
    }

    public ValidadorEgresos(){

    }

    public void nuevoEgresoPendiente(Egreso egreso){
        this.egresosPendientes.add(egreso);
    }

    public void agregarValidacion(ValidacionEgreso validacion){
        this.validaciones.add(validacion);
    }

    public void validarEgresosPendientes(){
        for (Egreso egresoPendiente : this.egresosPendientes) {
            try {
                this.validaciones.stream().forEach(validacion -> validacion.validar(egresoPendiente));
                egresoPendiente.setEstado(EstadoEgreso.ACEPTADO);
                egresoPendiente.getRevisor().nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue ACEPTADO");
            }
            catch(RuntimeException ex){
                egresoPendiente.setEstado(EstadoEgreso.RECHAZADO);
                egresoPendiente.getRevisor().nuevoMensaje("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: " + ex.getMessage());
            }
        }
    }

    public List<Egreso> getEgresosPendientes() {
        return egresosPendientes;
    }

    public List<ValidacionEgreso> getValidaciones() {
        return validaciones;
    }
}
