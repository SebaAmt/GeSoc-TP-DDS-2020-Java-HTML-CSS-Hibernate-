package dds.egreso;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.documentoComercial.DocumentoComercial;
import dds.mediosDePago.MedioDePago;

public class Egreso {
	
	private LocalDate fechaDeOperacion;
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private MedioDePago medioDePago;
	private List<Item> items = new ArrayList<>();
	
	
	public Egreso(LocalDate fechaDeOperacion, Proveedor proveedor, DocumentoComercial documentoComercial, MedioDePago medioDePago, List<Item> items) {
		this.fechaDeOperacion = fechaDeOperacion;
		this.proveedor = proveedor;
		this.documentoComercial = documentoComercial;
		this.medioDePago = medioDePago;
		this.items = items;
	}
	
	
	public BigDecimal valorTotal() {
		return items.stream().map(item->item.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void agregarItem(Item nuevoItem) {
		items.add(nuevoItem);
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
}