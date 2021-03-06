package model.egreso.test;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.exception.PresupuestoNoTieneMismosItemsQueEgresoException;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;

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

    private CreadorDePresupuestos creadorPresupuesto;
    private List<Item> items1 = new ArrayList<Item>();
    private List<Item> items2 = new ArrayList<Item>();
    private List<Item> items3 = new ArrayList<Item>();
    private Egreso egreso;
    private Proveedor proveedor;
    private CreadorMoneda creadorDolares;
    private Moneda moneda;
    
    @BeforeEach
    public void init() {
    	proveedor = new Proveedor("Telas SA", 30258741, null);
        creadorDolares = new CreadorMoneda(CurrencyID.USD);
        moneda = creadorDolares.getMoneda();
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");

        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));

        items2.add(new Item("Pegamento", new BigDecimal(50), 2));

        items3.add(new Item("Madera", new BigDecimal(80), 5));
        items3.add(new Item("Clavos", new BigDecimal(8), 10));
        items3.add(new Item("Martillo", new BigDecimal(35), 1));

        egreso = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, items1, new ArrayList<>(), new ArrayList<>(), true, null);
        creadorPresupuesto = new CreadorDePresupuestos();
        creadorPresupuesto.agregarDocumentoComercial(factura);
        creadorPresupuesto.agregarEgreso(egreso);
        creadorPresupuesto.agregarProveedor(proveedor);
        creadorPresupuesto.agregarMoneda(moneda);
    }

    @Test
    @DisplayName("Se obtiene mensaje de error al crear presupuesto para un egreso que no tiene los mismos items")
    public void MensajeDeErrorPresupuestoConItemsDistintosAEgreso() {
        creadorPresupuesto.agregarItems(items2);
        Presupuesto nuevoPresupuesto = creadorPresupuesto.crearPresupuesto();
        assertThrows(PresupuestoNoTieneMismosItemsQueEgresoException.class, () -> egreso.agregarPresupuesto(nuevoPresupuesto));
    }

    @Test
    @DisplayName("Se puede crear un presupuesto y agregarlo a un egreso con mismos items pero diferentes precios y orden")
    public void CrearPresupuestoConMismosItemsDistintoPrecio() {
        creadorPresupuesto.agregarItems(items3);
        Presupuesto nuevoPresupuesto = creadorPresupuesto.crearPresupuesto();
        egreso.agregarPresupuesto(nuevoPresupuesto);
        assertEquals(egreso.getPresupuestos().contains(nuevoPresupuesto), true);
    }

}

