package dds.egreso;

import dds.documentoComercial.DocumentoComercial;
import dds.exception.PresupuestoNoTieneMismaMoneda;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgreso;
import dds.mediosDePago.MedioDePago;
import dds.pais.Moneda;
import dds.usuario.Usuario;
import dds.validacionesEgresos.ValidadorEgresos;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CreadorEgreso {

    private Usuario revisor;
    private List<Presupuesto> presupuestos = new ArrayList<>();
    private boolean requierePresupuestos;
    private LocalDate fechaDeOperacion;
    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;
    private MedioDePago medioDePago;
    private List<Item> items = new ArrayList<>();
    private ValidadorEgresos validadorEgresos;
    private CriterioSeleccionPresupuesto criterio;

    public CreadorEgreso(LocalDate fechaDeOperacion, Proveedor proveedor, 
    		DocumentoComercial documentoComercial, MedioDePago medioDePago, List<Item> items, ValidadorEgresos validadorEgresos) {
        this.fechaDeOperacion = Objects.requireNonNull(fechaDeOperacion, "Debe cargarse la fecha de operación");
        this.proveedor = Objects.requireNonNull(proveedor, "Debe cargarse un proveedor");
        this.documentoComercial = Objects.requireNonNull(documentoComercial, "Debe cargarse un documento comercial");
        this.medioDePago = Objects.requireNonNull(medioDePago, "Debe cargarse el medio de pago");
        this.items = Objects.requireNonNull(items, "Deben cargarse los items");
        this.validadorEgresos = validadorEgresos;
    }

    public void agregarPresupuesto(Presupuesto presupuesto){
        tieneLosMismosItems(presupuesto);
        tienenMismaMoneda(presupuesto);
        this.presupuestos.add(presupuesto);
    }

    private void tieneLosMismosItems(Presupuesto presupuesto){
        Map<String, Integer> articulosEgreso = new HashMap<>();
        this.items.stream().forEach(item -> articulosEgreso.put(item.getDescripcion(), item.getCantidadUnidades()));
        Map<String, Integer> articulosPresupuesto = new HashMap<>();
        presupuesto.getItems().stream().forEach(item -> articulosPresupuesto.put(item.getDescripcion(), item.getCantidadUnidades()));

        if(!articulosEgreso.equals(articulosPresupuesto)) { 
            throw new PresupuestoNoTieneMismosItemsQueEgreso();
        }
    }
    
    private void tienenMismaMoneda(Presupuesto presupuesto) {
    	Moneda monedaProveedor = proveedor.getDireccionPostal().getPais().getMoneda();
    	Moneda monedaPresupuesto = presupuesto.getMoneda();
    	
    	if(!monedaProveedor.equals(monedaPresupuesto)) {
    		throw new PresupuestoNoTieneMismaMoneda();
    	};
    }

    public void requierePresupuestos(){
        this.requierePresupuestos = true;
    }

    public void asignarCriterioSeleccionPresupuesto(CriterioSeleccionPresupuesto criterio){
        this.criterio = criterio;
    }

    public void asignarUsuarioRevisor(Usuario revisor){
        this.revisor = revisor;
    }

    public Egreso crearEgreso(){
        if(this.requierePresupuestos){
            Objects.requireNonNull(this.validadorEgresos, "Se requiere un validador de egresos");
            Objects.requireNonNull(this.revisor, "Se requiere un revisor");
            Egreso nuevoEgreso = new Egreso(this.fechaDeOperacion, this.proveedor, this.documentoComercial, this.medioDePago, this.items, this.revisor, this.presupuestos, true, EstadoEgreso.PENDIENTE, this.criterio);
            validadorEgresos.nuevoEgresoPendiente(nuevoEgreso);
            return nuevoEgreso;
        }
        return new Egreso(this.fechaDeOperacion, this.proveedor, this.documentoComercial, this.medioDePago, this.items, this.revisor, this.presupuestos, false, EstadoEgreso.ACEPTADO, this.criterio);
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }
}
