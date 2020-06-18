package dds.egreso;

import dds.documentoComercial.DocumentoComercial;
import dds.mediosDePago.MedioDePago;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Presupuesto {

    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;

    public List<Item> getItems() {
        return items;
    }

    private List<Item> items = new ArrayList<>();

    public Presupuesto(Proveedor proveedor, DocumentoComercial documentoComercial, List<Item> items) {
        this.proveedor = proveedor;
        this.documentoComercial = documentoComercial;
        this.items = items;
    }

    public BigDecimal valorTotal() {
        return items.stream().map(item->item.valorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }



}
