package dds.egreso;

import dds.documentoComercial.DocumentoComercial;
import dds.mediosDePago.MedioDePago;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Presupuesto {

    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;
    private List<Item> items = new ArrayList<>();
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

    public BigDecimal valorTotal() {
        return items.stream().map(item->item.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public Moneda getMoneda() {
    	return this.moneda;
    }
}
