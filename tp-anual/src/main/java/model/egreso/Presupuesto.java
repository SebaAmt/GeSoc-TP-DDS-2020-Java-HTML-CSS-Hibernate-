package model.egreso;

import model.documentoComercial.DocumentoComercial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
	@GeneratedValue
	private Long id;
	@ManyToOne
    private Proveedor proveedor;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentoComercial_id", referencedColumnName = "id")
    private DocumentoComercial documentoComercial;
    @OneToMany
    @JoinColumn(name = "presupuesto_id")
    private List<Item> items = new ArrayList<>();
    @ManyToOne
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

    public void agregarItem(Item item){
        this.items.add(item);
    }

    public Long getId() {
        return id;
    }
}
