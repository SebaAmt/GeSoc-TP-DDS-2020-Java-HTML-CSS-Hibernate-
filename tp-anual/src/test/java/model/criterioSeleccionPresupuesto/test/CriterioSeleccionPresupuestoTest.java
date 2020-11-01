package model.criterioSeleccionPresupuesto.test;

import model.direccion.Direccion;
import model.documentoComercial.DocumentoComercial;

import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CriterioSeleccionPresupuestoTest {
    private List<Presupuesto> presupuestos = new ArrayList<>();
    private Presupuesto presupuesto1;
    private Presupuesto presupuesto2;
    private Presupuesto presupuesto3;
    private Direccion direccion;
    private CreadorMoneda creadorPesos;
    private Moneda moneda;

    @BeforeEach
    public void init() {
    	creadorPesos = new CreadorMoneda(CurrencyID.ARS);
    	moneda = creadorPesos.getMoneda();
    	direccion = null;
        Proveedor proveedor = new Proveedor("Proveedor", 11111111, direccion);
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        

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

        presupuesto1 = new Presupuesto(proveedor, factura, moneda, items1);
        presupuesto2 = new Presupuesto(proveedor, factura, moneda, items2);
        presupuesto3 = new Presupuesto(proveedor, factura, moneda, items3);
        this.presupuestos.add(presupuesto1);
        this.presupuestos.add(presupuesto2);
        this.presupuestos.add(presupuesto3);
    }

    @Test
    @DisplayName("Criterio Presupuesto Menor Valor: se selecciona de la lista el presupuesto de menor valor total")
    public void PresupuestoSeleccionadoEsElDeMenorValor() {
        CriterioSeleccionPresupuesto menorValor = CriterioSeleccionPresupuesto.MENOR_VALOR;
        assertEquals(menorValor.seleccionarPresupuesto(this.presupuestos), presupuesto1);
    }

}
