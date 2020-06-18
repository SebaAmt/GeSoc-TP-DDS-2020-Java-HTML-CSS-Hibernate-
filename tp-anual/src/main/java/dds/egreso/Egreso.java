package dds.egreso;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.documentoComercial.DocumentoComercial;
import dds.mediosDePago.MedioDePago;
import dds.usuario.Usuario;

public class Egreso {

	private Usuario revisor;

	private List<Presupuesto> presupuestos;
	private LocalDate fechaDeOperacion;
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private MedioDePago medioDePago;
	private List<Item> items = new ArrayList<>();
	private boolean requierePresupuestos;
	private EstadoEgreso estado;
	private CriterioSeleccionPresupuesto criterio;


	public Egreso(LocalDate fechaDeOperacion, Proveedor proveedor, DocumentoComercial documentoComercial, MedioDePago medioDePago, List<Item> items, Usuario revisor, List<Presupuesto> presupuestos, boolean requierePresupuestos, EstadoEgreso estado, CriterioSeleccionPresupuesto criterio) {
		this.fechaDeOperacion = fechaDeOperacion;
		this.proveedor = proveedor;
		this.documentoComercial = documentoComercial;
		this.medioDePago = medioDePago;
		this.items = items;
		this.revisor = revisor;
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

	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void setEstado(EstadoEgreso estado) {
		this.estado = estado;
	}

	public Usuario getRevisor() {
		return revisor;
	}

	public CriterioSeleccionPresupuesto getCriterio() {
		return criterio;
	}
	
	@Override
	public String toString() {
		return "Egreso{" +
				"revisor=" + revisor +
				", fechaDeOperacion=" + fechaDeOperacion +
				'}';
	}
}