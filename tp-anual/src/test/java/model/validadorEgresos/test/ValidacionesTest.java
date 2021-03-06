package model.validadorEgresos.test;

import model.documentoComercial.DocumentoComercial;

import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.exception.ValidacionEgresoFallidaException;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import model.validacionesEgresos.ValidacionEgreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidacionesTest {
    private Egreso egresoSinCriterioYDistintoProveedor;
    private Egreso egresoSinCriterioYValorTotalDistinto;
    private Egreso egresoConPresupuestoDistintoAlDevueltoPorCriterio;
    private Egreso egresoSinCantidadMinimaDePresupuestos;
    private ValidacionEgreso cantidadMinimaPresupuestos = ValidacionEgreso.CANTIDAD_MINIMA;
    private ValidacionEgreso relacionEgresoPresupuesto = ValidacionEgreso.COINCIDE_CON_PRESUPUESTO_CARGADO;
    private ValidacionEgreso relacionEgresoPresupuestoSegunCriterio = ValidacionEgreso.COINCIDE_CON_CRITERIO;
    private CreadorMoneda creadorPesos;
    private Moneda moneda;
    private Proveedor proveedor1, proveedor2;

    @BeforeEach
    public void init() {
        proveedor1 = new Proveedor("Telas SA", 30258741, null);
        proveedor2 = new Proveedor("Edenor", 40987654, null);
        creadorPesos = new CreadorMoneda(CurrencyID.ARS);
        moneda = creadorPesos.getMoneda();
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");

        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));

        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Madera", new BigDecimal(80), 5));
        items2.add(new Item("Clavos", new BigDecimal(8), 10));
        items2.add(new Item("Martillo", new BigDecimal(35), 1));

        List<Item> items3 = new ArrayList<Item>();
        items3.add(new Item("Madera", new BigDecimal(1), 5));
        items3.add(new Item("Clavos", new BigDecimal(1), 10));
        items3.add(new Item("Martillo", new BigDecimal(1), 1));

        List<Item> items4 = new ArrayList<Item>();
        items4.add(new Item("Madera", new BigDecimal(250), 5));
        items4.add(new Item("Clavos", new BigDecimal(1), 10));
        items4.add(new Item("Martillo", new BigDecimal(12), 1));

        Presupuesto presupuesto1 = new Presupuesto(proveedor1, factura, moneda, items1);
        Presupuesto presupuesto2 = new Presupuesto(proveedor1, factura, moneda, items2);
        Presupuesto presupuesto3 = new Presupuesto(proveedor1, factura, moneda, items3);

        List<Presupuesto> presupuestos = new ArrayList<>();
        presupuestos.add(presupuesto1);
        presupuestos.add(presupuesto2);
        presupuestos.add(presupuesto3);

        egresoSinCriterioYDistintoProveedor = new Egreso(LocalDate.now(), proveedor2, factura, efectivo, moneda, items1, null, presupuestos, true,null);
        egresoSinCriterioYValorTotalDistinto = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, moneda, items4, null, presupuestos, true, null);
        egresoConPresupuestoDistintoAlDevueltoPorCriterio = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, moneda, items2, null, presupuestos, true, CriterioSeleccionPresupuesto.MENOR_VALOR);

        List<Presupuesto> presupuestosMenorMinimo = new ArrayList<>();
        presupuestosMenorMinimo.add(presupuesto1);
        presupuestosMenorMinimo.add(presupuesto3);
        egresoSinCantidadMinimaDePresupuestos = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, moneda, items2, null, presupuestosMenorMinimo, true, CriterioSeleccionPresupuesto.MENOR_VALOR);
    }

    @Test
    @DisplayName("Egreso sin criterio y con proveedor distinto a los presupuestos cargados arroja error al ser validado")
    public void EgresoSinCriterioNiPresupuestoCorrectoArrojaErrorPorTotal() {
        assertThrows(ValidacionEgresoFallidaException.class, () -> relacionEgresoPresupuesto.validar(egresoSinCriterioYDistintoProveedor), "El egreso no se corresponde con ninguno de los presupuestos cargados");
    }

    @Test
    @DisplayName("Egreso sin criterio y con total distinto a los presupuestos cargados arroja error al ser validado")
    public void EgresoSinCriterioNiPresupuestoCorrectoArrojaErrorPorProveedor() {
        assertThrows(ValidacionEgresoFallidaException.class, () -> relacionEgresoPresupuesto.validar(egresoSinCriterioYValorTotalDistinto), "El egreso no se corresponde con ninguno de los presupuestos cargados");
    }

    @Test
    @DisplayName("Egreso con criterio y hecho en base a presupuesto distinto al seleccionado arroja error al ser validado")
    public void EgresoConCriterioSinPresupuestoCorrectoElegidoArrojaError() {
        assertThrows(ValidacionEgresoFallidaException.class, () -> relacionEgresoPresupuestoSegunCriterio.validar(egresoConPresupuestoDistintoAlDevueltoPorCriterio), "El egreso no se corresponde con el presupuesto seleccionado por criterio");
    }

    @Test
    @DisplayName("Egreso con menos presupuestos cargados que el minimo definido arroja error al validarse")
    public void EgresoConMenosPresupuestosQueElMinimoArrojaError() {
        assertThrows(ValidacionEgresoFallidaException.class, () -> cantidadMinimaPresupuestos.validar(egresoSinCantidadMinimaDePresupuestos), "El egreso no cumple con la cantidad minima de presupuestos cargados");
    }

}
