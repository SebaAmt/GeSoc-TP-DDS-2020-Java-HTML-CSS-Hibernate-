package dds.egreso;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dds.documentoComercial.DocumentoComercial;
import dds.exception.PresupuestoNoTieneMismaMonedaException;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgresoException;
import dds.mediosDePago.MedioDePago;
import dds.usuario.Usuario;

public class Egreso {

	private List<Usuario> revisores;
	private List<Presupuesto> presupuestos = new ArrayList<>();
	private LocalDate fechaDeOperacion;
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private MedioDePago medioDePago;
	private Moneda moneda;
	private List<Item> items = new ArrayList<>();
	private boolean requierePresupuestos;
	private EstadoEgreso estado;
	private CriterioSeleccionPresupuesto criterio;


	public Egreso(LocalDate fechaDeOperacion, Proveedor proveedor, DocumentoComercial documentoComercial, MedioDePago medioDePago, Moneda moneda, List<Item> items, List<Usuario> revisores, List<Presupuesto> presupuestos, boolean requierePresupuestos, CriterioSeleccionPresupuesto criterio) {
		this.fechaDeOperacion = fechaDeOperacion;
		this.proveedor = proveedor;
		this.documentoComercial = documentoComercial;
		this.medioDePago = medioDePago;
		this.moneda = moneda;
		this.items = items;
		this.revisores = revisores;
		this.presupuestos = presupuestos;
		this.requierePresupuestos = requierePresupuestos;
		this.estado = (requierePresupuestos) ? EstadoEgreso.PENDIENTE : EstadoEgreso.ACEPTADO;
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
		if(this.requierePresupuestos)
			this.estado = EstadoEgreso.PENDIENTE;
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
		return this.moneda;
	}

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void cambiarEstado(EstadoEgreso estado, String mensaje)
	{
		this.estado = estado;
		this.informarARevisores(mensaje);
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
				"Documento Comercial= " + this.documentoComercial.getTipoDocumentoComercial() + " - Nro. " + this.documentoComercial.getIdentificadorDocumento() +

				", Fecha de operacion=" + fechaDeOperacion +
				'}';
	}

	private void tieneLosMismosItems(Presupuesto presupuesto){
		Map<String, Integer> articulosEgreso = new HashMap<>();
		this.items.stream().forEach(item -> articulosEgreso.put(item.getDescripcion(), item.getCantidadUnidades()));
		Map<String, Integer> articulosPresupuesto = new HashMap<>();
		presupuesto.getItems().stream().forEach(item -> articulosPresupuesto.put(item.getDescripcion(), item.getCantidadUnidades()));

		if(!articulosEgreso.equals(articulosPresupuesto))
			throw new PresupuestoNoTieneMismosItemsQueEgresoException();
	}

	private void tienenMismaMoneda(Presupuesto presupuesto) {
		Moneda monedaEgreso = this.getMoneda();
		Moneda monedaPresupuesto = presupuesto.getMoneda();

		if(!monedaEgreso.equals(monedaPresupuesto)) {
			throw new PresupuestoNoTieneMismaMonedaException();
		};
	}

	public void agregarRevisor(Usuario nuevoRevisor){
		this.revisores.add(nuevoRevisor);
	}

	public void informarARevisores(String mensaje){
		this.revisores.forEach(revisor -> revisor.nuevoMensaje(mensaje));
	}

	public boolean requierePresupuestos(){
		return this.requierePresupuestos;
	}

	public boolean tieneMismoValorTotal(Presupuesto presupuesto){
		return this.valorTotal().compareTo(presupuesto.valorTotal()) == 0;
	}

	public boolean tieneMismoProveedor(Presupuesto presupuesto){
		return this.getProveedor() == presupuesto.getProveedor();
	}

}