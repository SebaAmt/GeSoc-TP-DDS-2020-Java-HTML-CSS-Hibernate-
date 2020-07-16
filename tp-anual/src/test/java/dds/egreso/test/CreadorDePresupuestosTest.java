package dds.egreso.test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.*;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgresoException;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.pais.Moneda;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreadorDePresupuestosTest {
    
	private CreadorProveedor creadorProveedor;
    private CreadorDePresupuestos creadorPresupuesto;
    private List<Item> items1 = new ArrayList<Item>();
    private List<Item> items2 = new ArrayList<Item>();
    private List<Item> items3 = new ArrayList<Item>();
    private Egreso egreso;

    @BeforeEach
    public void init() {
        creadorProveedor = new CreadorProveedor();
        Proveedor proveedor = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
        Moneda moneda = proveedor.getDireccionPostal().getPais().getMoneda();
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");

        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));

        items2.add(new Item("Pegamento", new BigDecimal(50), 2));

        items3.add(new Item("Madera", new BigDecimal(80), 5));
        items3.add(new Item("Clavos", new BigDecimal(8), 10));
        items3.add(new Item("Martillo", new BigDecimal(35), 1));

        egreso = new Egreso(LocalDate.now(), proveedor, factura, efectivo, items1, new ArrayList<>(), new ArrayList<>(), true, null);
        creadorPresupuesto = new CreadorDePresupuestos();
        creadorPresupuesto.agregarDocumentoComercial(factura);
        creadorPresupuesto.agregarEgreso(egreso);
        creadorPresupuesto.agregarProveedor(proveedor);
    }

    @Test
    @DisplayName("Se obtiene mensaje de error al crear presupuesto para un egreso que no tiene los mismos items")
    public void MensajeDeErrorPresupuestoConItemsDistintosAEgreso() {
        creadorPresupuesto.agregarItems(items2);
        assertThrows(PresupuestoNoTieneMismosItemsQueEgresoException.class, () -> creadorPresupuesto.crearPresupuesto());
    }

    @Test
    @DisplayName("Se puede crear un presupuesto y agregarlo a un egreso con mismos items pero diferentes precios y orden")
    public void CrearPresupuestoConMismosItemsDistintoPrecio() {
        creadorPresupuesto.agregarItems(items3);
        Presupuesto nuevoPresupuesto = creadorPresupuesto.crearPresupuesto();
        assertEquals(egreso.getPresupuestos().contains(nuevoPresupuesto), true);
    }

}

