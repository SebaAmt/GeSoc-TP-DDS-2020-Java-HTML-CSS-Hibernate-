package dds.egreso;

import dds.documentoComercial.DocumentoComercial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "presupuestos")
public class Presupuesto {

	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
    private Proveedor proveedor;
	@OneToOne
    private DocumentoComercial documentoComercial;
    @OneToMany
    @JoinColumn(name = "presupuesto_id")
    private List<Item> items = new ArrayList<>();
    @OneToOne
    private Moneda moneda;

    public List<Item> getItems() {
        return items;
    }

    public Presupuesto(Proveedor proveedor, DocumentoComercial documentoComercial, Moneda moneda, List<Item> items) {
        this.proveedor = proveedor;
        this.documentoComercial = documentoComercial;
        this.moneda = moneda;
        this.items = items;
    }
    
    public Presupuesto() {
    }

    public BigDecimal valorTotal() {
        return items.stream().map(item->item.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public DocumentoComercial getDocumentoComercial() {
    	return this.documentoComercial;
    }
    
    public Moneda getMoneda() {
    	return this.moneda;
    }
}
