package dds.egreso;

import dds.documentoComercial.DocumentoComercial;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgreso;
import dds.mediosDePago.MedioDePago;

import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.*;

public class CreadorDePresupuestos {

    private Egreso egreso;
    private Proveedor proveedor;
    private DocumentoComercial documentoComercial;
    private List<Item> items = new ArrayList<>();

    public CreadorDePresupuestos(){}

    public CreadorDePresupuestos(Proveedor proveedor, DocumentoComercial documentoComercial, List<Item> items) {
        this.proveedor = Objects.requireNonNull(proveedor, "Debe cargarse un proveedor");
        this.documentoComercial = Objects.requireNonNull(documentoComercial, "Debe cargarse un documento comercial");
        this.items = Objects.requireNonNull(items, "Deben cargarse los items");
    }

    public void agregarProveedor(Proveedor proveedor){
        this.proveedor = proveedor;
    }

    public void agregarDocumentoComercial(DocumentoComercial documento){
        this.documentoComercial = documento;
    }

    public void agregarItem(Item item){
        this.items.add(item);
    }

    public void agregarEgreso(Egreso egreso){
        this.egreso = egreso;
    }

    public Presupuesto CrearPresupuesto(){
        Objects.requireNonNull(this.egreso, "El presupuesto debe pertenecer a un egreso");
        Objects.requireNonNull(this.documentoComercial, "El presupuesto debe tener un documento comercial");
        Objects.requireNonNull(this.proveedor, "El presupuesto debe tener un proveedor");
        Objects.requireNonNull(this.items, "El presupuesto debe tener al menos un item");
        Presupuesto nuevoPresupuesto = new Presupuesto(this.proveedor, this.documentoComercial, this.items);
        this.egreso.agregarPresupuesto(nuevoPresupuesto);
        return nuevoPresupuesto;
    }

}


