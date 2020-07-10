package dds.validadorEgresos.test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.*;
import dds.exception.EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio;
import dds.exception.EgresoNoCorrespondeConPresupuestoCargado;
import dds.exception.EgresoNoCumpleCantidadMinimaDePresupuestos;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.validacionesEgresos.EgresoCoincideConAlgunPresupuestoCargado;
import dds.validacionesEgresos.EgresoCoincideConPresupuestoSeleccionadoPorCriterio;
import dds.validacionesEgresos.EgresoTieneCantidadMinimaDePresupuestos;
import dds.validacionesEgresos.ValidacionEgreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidacionesTest {
    private Egreso egresoSinCriterioYDistintoProveedor;
    private Egreso egresoSinCriterioYValorTotalDistinto;
    private Egreso egresoConPresupuestoDistintoAlDevueltoPorCriterio;
    private Egreso egresoSinCantidadMinimaDePresupuestos;
    private ValidacionEgreso cantidadMinimaPresupuestos = new EgresoTieneCantidadMinimaDePresupuestos();
    private ValidacionEgreso relacionEgresoPresupuesto = new EgresoCoincideConAlgunPresupuestoCargado();
    private ValidacionEgreso relacionEgresoPresupuestoSegunCriterio = new EgresoCoincideConPresupuestoSeleccionadoPorCriterio();
    private CreadorProveedor creadorProveedor;

    @BeforeEach
    public void init() {
    	creadorProveedor = new CreadorProveedor();
        Proveedor proveedor1 = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
        Proveedor proveedor2 = creadorProveedor.crearProveedor("Edenor", 40987654, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkNBQjM4MDda", "Av Rivadavia", 4400, null, null,"8520");
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

        Presupuesto presupuesto1 = new Presupuesto(proveedor1, factura, items1);
        Presupuesto presupuesto2 = new Presupuesto(proveedor1, factura, items2);
        Presupuesto presupuesto3 = new Presupuesto(proveedor1, factura, items3);

        List<Presupuesto> presupuestos = new ArrayList<>();
        presupuestos.add(presupuesto1);
        presupuestos.add(presupuesto2);
        presupuestos.add(presupuesto3);

        egresoSinCriterioYDistintoProveedor = new Egreso(LocalDate.now(), proveedor2, factura, efectivo, items1, null, presupuestos, true,null);
        egresoSinCriterioYValorTotalDistinto = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, items4, null, presupuestos, true, null);
        egresoConPresupuestoDistintoAlDevueltoPorCriterio = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, items2, null, presupuestos, true, new CriterioPresupuestoMenorValor());

        List<Presupuesto> presupuestosMenorMinimo = new ArrayList<>();
        presupuestosMenorMinimo.add(presupuesto1);
        presupuestosMenorMinimo.add(presupuesto3);
        egresoSinCantidadMinimaDePresupuestos = new Egreso(LocalDate.now(), proveedor1, factura, efectivo, items2, null, presupuestosMenorMinimo, true, new CriterioPresupuestoMenorValor());
    }

    @Test
    @DisplayName("Egreso sin criterio y con proveedor distinto a los presupuestos cargados arroja error al ser validado")
    public void EgresoSinCriterioNiPresupuestoCorrectoArrojaErrorPorTotal() {
        assertThrows(EgresoNoCorrespondeConPresupuestoCargado.class, () -> relacionEgresoPresupuesto.validar(egresoSinCriterioYDistintoProveedor));
    }

    @Test
    @DisplayName("Egreso sin criterio y con total distinto a los presupuestos cargados arroja error al ser validado")
    public void EgresoSinCriterioNiPresupuestoCorrectoArrojaErrorPorProveedor() {
        assertThrows(EgresoNoCorrespondeConPresupuestoCargado.class, () -> relacionEgresoPresupuesto.validar(egresoSinCriterioYValorTotalDistinto));
    }

    @Test
    @DisplayName("Egreso con criterio y hecho en base a presupuesto distinto al seleccionado arroja error al ser validado")
    public void EgresoConCriterioSinPresupuestoCorrectoElegidoArrojaError() {
        assertThrows(EgresoNoCorrespondeAPresupuestoSeleccionadoPorCriterio.class, () -> relacionEgresoPresupuestoSegunCriterio.validar(egresoConPresupuestoDistintoAlDevueltoPorCriterio));
    }

    @Test
    @DisplayName("Egreso con menos presupuestos cargados que el minimo definido arroja error al validarse")
    public void EgresoConMenosPresupuestosQueElMinimoArrojaError() {
        assertThrows(EgresoNoCumpleCantidadMinimaDePresupuestos.class, () -> cantidadMinimaPresupuestos.validar(egresoSinCantidadMinimaDePresupuestos));
    }

}
