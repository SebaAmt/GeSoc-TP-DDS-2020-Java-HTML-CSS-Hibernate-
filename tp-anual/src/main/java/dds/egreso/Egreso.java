package dds.egreso;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.documentoComercial.DocumentoComercial;
import dds.exception.PresupuestoNoTieneMismaMoneda;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgreso;
import dds.mediosDePago.MedioDePago;
import dds.pais.Moneda;
import dds.usuario.Usuario;

public class Egreso {

	private List<Usuario> revisores;
	private List<Presupuesto> presupuestos = new ArrayList<>();
	private LocalDate fechaDeOperacion;
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private MedioDePago medioDePago;
	private List<Item> items = new ArrayList<>();
	private boolean requierePresupuestos;
	private EstadoEgreso estado;
	private CriterioSeleccionPresupuesto criterio;


	public Egreso(LocalDate fechaDeOperacion, Proveedor proveedor, DocumentoComercial documentoComercial, MedioDePago medioDePago, List<Item> items, List<Usuario> revisores, List<Presupuesto> presupuestos, boolean requierePresupuestos, EstadoEgreso estado, CriterioSeleccionPresupuesto criterio) {
		this.fechaDeOperacion = fechaDeOperacion;
		this.proveedor = proveedor;
		this.documentoComercial = documentoComercial;
		this.medioDePago = medioDePago;
		this.items = items;
		this.revisores = revisores;
		this.presupuestos = presupuestos;
		this.requierePresupuestos = requierePresupuestos;
		this.estado = estado;
		this.criterio = criterio;
	}
	
	public BigDecimal valorTotal() {
		return items.stream().map(item->item.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void agregarItem(Item nuevoItem) {
		items.add(nuevoItem);
	}
		
	public void agregarPresupuesto(Presupuesto presupuesto){
		this.tieneLosMismosItems(presupuesto);
		this.tienenMismaMoneda(presupuesto);
		this.presupuestos.add(presupuesto);
	}

	public LocalDate getFechaDeOperacion() {
		return fechaDeOperacion;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public DocumentoComercial getDocumentoComercial() {
		return documentoComercial;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public Moneda getMoneda() {
		return getProveedor().getDireccionPostal().getPais().getMoneda();
	}

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void setEstado(EstadoEgreso estado) {
		this.estado = estado;
	}
	
	public CriterioSeleccionPresupuesto getCriterio() {
		return criterio;
	}

	public EstadoEgreso getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Egreso{" +
				"revisores=" + this.revisores +
				", fechaDeOperacion=" + fechaDeOperacion +
				'}';
	}

	private void tieneLosMismosItems(Presupuesto presupuesto){
		Map<String, Integer> articulosEgreso = new HashMap<>();
		this.items.stream().forEach(item -> articulosEgreso.put(item.getDescripcion(), item.getCantidadUnidades()));
		Map<String, Integer> articulosPresupuesto = new HashMap<>();
		presupuesto.getItems().stream().forEach(item -> articulosPresupuesto.put(item.getDescripcion(), item.getCantidadUnidades()));

		if(!articulosEgreso.equals(articulosPresupuesto))
			throw new PresupuestoNoTieneMismosItemsQueEgreso();
	}

	private void tienenMismaMoneda(Presupuesto presupuesto) {
		Moneda monedaProveedor = proveedor.getDireccionPostal().getPais().getMoneda();
		Moneda monedaPresupuesto = presupuesto.getMoneda();

		if(!monedaProveedor.equals(monedaPresupuesto)) {
			throw new PresupuestoNoTieneMismaMoneda();
		};
	}

	public void agregarRevisor(Usuario nuevoRevisor){
		this.revisores.add(nuevoRevisor);
	}
	
	public void informarARevisores(String mensaje){
		this.revisores.forEach(revisor -> revisor.nuevoMensaje(mensaje));
	}
}