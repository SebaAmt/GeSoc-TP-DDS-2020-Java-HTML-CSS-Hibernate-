package model.egreso;

import model.documentoComercial.DocumentoComercial;

import java.util.*;

public class CreadorDePresupuestos {

    private Egreso egreso;
    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;
    private Moneda moneda;
    private List<Item> items = new ArrayList<>();

    public CreadorDePresupuestos(){}

    public void agregarProveedor(Proveedor proveedor){
        this.proveedor = proveedor;
    }

    public void agregarDocumentoComercial(DocumentoComercial documento){
        this.documentoComercial = documento;
    }

    public void agregarItem(Item item){
        this.items.add(item);
    }

    public void agregarItems(List<Item> items){
        items.forEach(i -> this.items.add(i));
    }

    public void agregarEgreso(Egreso egreso){
        this.egreso = egreso;
    }
    
    public void agregarMoneda(Moneda moneda) {
    	this.moneda = moneda;
    }
    
    public Presupuesto crearPresupuesto(){
        Objects.requireNonNull(this.egreso, "El presupuesto debe pertenecer a un egreso");
        Objects.requireNonNull(this.documentoComercial, "El presupuesto debe tener un documento comercial");
        Objects.requireNonNull(this.moneda, "El presupuesto debe tener una moneda");
        Objects.requireNonNull(this.proveedor, "El presupuesto debe tener un proveedor");
        Objects.requireNonNull(this.items, "El presupuesto debe tener al menos un item");
        Presupuesto nuevoPresupuesto = new Presupuesto(this.proveedor, this.documentoComercial, this.moneda, this.items);
        return nuevoPresupuesto;
    }

}


