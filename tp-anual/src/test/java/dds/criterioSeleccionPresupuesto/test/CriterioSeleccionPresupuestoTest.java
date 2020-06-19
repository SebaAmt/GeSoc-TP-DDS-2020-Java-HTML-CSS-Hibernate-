package dds.criterioSeleccionPresupuesto.test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.*;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgreso;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.validacionesEgresos.ValidadorEgresos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CriterioSeleccionPresupuestoTest {
    private List<Presupuesto> presupuestos = new ArrayList<>();
    private Presupuesto presupuesto1;
    private Presupuesto presupuesto2;
    private Presupuesto presupuesto3;

    @BeforeEach
    public void init() {
        Proveedor proveedor = new Proveedor("Proveedor", 11111111, "Direccion 123");
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");

        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(5), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(5), 5));

        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Madera", new BigDecimal(80), 5));
        items2.add(new Item("Clavos", new BigDecimal(21), 10));
        items2.add(new Item("Martillo", new BigDecimal(35), 1));

        List<Item> items3 = new ArrayList<Item>();
        items3.add(new Item("Madera", new BigDecimal(150), 5));
        items3.add(new Item("Clavos", new BigDecimal(20), 10));
        items3.add(new Item("Martillo", new BigDecimal(1), 1));

        presupuesto1 = new Presupuesto(proveedor, factura, items1);
        presupuesto2 = new Presupuesto(proveedor, factura, items2);
        presupuesto3 = new Presupuesto(proveedor, factura, items3);
        this.presupuestos.add(presupuesto1);
        this.presupuestos.add(presupuesto2);
        this.presupuestos.add(presupuesto3);
    }

    @Test
    @DisplayName("Criterio Presupuesto Menor Valor: se selecciona de la lista el presupuesto de menor valor total")
    public void PresupuestoSeleccionadoEsElDeMenorValor() {
        CriterioSeleccionPresupuesto menorValor = new CriterioPresupuestoMenorValor();
        assertEquals(menorValor.seleccionarPresupuesto(this.presupuestos), presupuesto1);
    }

}
