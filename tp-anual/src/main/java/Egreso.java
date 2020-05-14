import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import dds.mediosDePago.MedioDePago;

public class Egreso {
	
	private LocalDate fechaDeOperacion;
	private Proveedor proveedor;
	private DocumentoComercial documentoComercial;
	private MedioDePago medioDePago;
	private List<Item> items;
	
	
	public Egreso(LocalDate fechaDeOperacion, Proveedor proveedor, DocumentoComercial documentoComercial, MedioDePago medioDePago, List<Item> items) {
		setFechaDeOperacion(fechaDeOperacion);
		setProveedor(proveedor);
		setDocumentoComercial(documentoComercial);
		setMedioDePago(medioDePago);
		setItems(items);
	}
	
	
	public BigDecimal valorTotal() {
		return items.stream().map(item->item.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public void agregarItem(Item nuevoItem) {
		items.add(nuevoItem);
	}
	
	public List<String> detalle() {
		return items.stream().map(item->item.getDescripcion()).collect(Collectors.toList());
	}
	
	
	public LocalDate getFechaDeOperacion() {
		return fechaDeOperacion;
	}
	
	public void setFechaDeOperacion(LocalDate fechaDeOperacion) {
		this.fechaDeOperacion = fechaDeOperacion;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	public DocumentoComercial getDocumentoComercial() {
		return documentoComercial;
	}

	public void setDocumentoComercial(DocumentoComercial documentoComercial) {
		this.documentoComercial = documentoComercial;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}	
}