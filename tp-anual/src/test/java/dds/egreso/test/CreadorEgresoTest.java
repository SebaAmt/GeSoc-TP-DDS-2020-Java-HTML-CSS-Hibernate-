package dds.egreso.test;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.*;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.exception.PasswordException;
import dds.exception.PresupuestoNoTieneMismosItemsQueEgreso;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.usuario.TipoUsuario;
import dds.validacionesEgresos.ValidacionEgreso;
import dds.validacionesEgresos.ValidadorEgresos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreadorEgresoTest {
    private CreadorEgreso creadorEgreso;
    private Presupuesto presupuesto1;
    private Presupuesto presupuesto2;
    private ValidadorEgresos validador;

    @BeforeEach
    public void init() {
        Proveedor proveedor = new Proveedor("Proveedor", 11111111, "Direccion 123");
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");
        List<Item> items1 = new ArrayList<Item>();
        items1.add(new Item("Martillo", new BigDecimal(30), 1));
        items1.add(new Item("Clavos", new BigDecimal(5), 10));
        items1.add(new Item("Madera", new BigDecimal(100), 5));
        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Item("Pegamento", new BigDecimal(50), 2));
        List<Item> items3 = new ArrayList<Item>();
        items3.add(new Item("Madera", new BigDecimal(80), 5));
        items3.add(new Item("Clavos", new BigDecimal(8), 10));
        items3.add(new Item("Martillo", new BigDecimal(35), 1));

        validador = new ValidadorEgresos();
        creadorEgreso = new CreadorEgreso(LocalDate.now(), proveedor, factura, efectivo, items1, validador);
        presupuesto1 = new Presupuesto(proveedor, factura, items2);
        presupuesto2 = new Presupuesto(proveedor, factura, items3);
    }

    @Test
    @DisplayName("Se obtiene mensaje de error al agregar un presupuesto con items distintos al egreso")
    public void MensajeDeErrorPresupuestoConItemsDistintosAEgreso() {
        assertThrows(PresupuestoNoTieneMismosItemsQueEgreso.class, () -> creadorEgreso.agregarPresupuesto(presupuesto1));
    }

    @Test
    @DisplayName("Se puede agregar un presupuesto con mismos items pero diferentes precios y orden")
    public void AgregarPresupuestoConMismosItemsDistintoPrecio() {
        creadorEgreso.agregarPresupuesto(presupuesto2);
        assertEquals(creadorEgreso.getPresupuestos().contains(presupuesto2), true);
    }

    @Test
    @DisplayName("Se crea un egreso (que requiere presupuesto) de manera definitiva: se agrega al validador con estado pendiente")
    public void CrearEgresoDefinitivoQueRequierePresupuesto() {
        creadorEgreso.agregarPresupuesto(presupuesto2);
        creadorEgreso.requierePresupuestos();
        Egreso nuevoEgreso = creadorEgreso.crearEgreso();
        assertEquals(validador.getEgresosPendientes().contains(nuevoEgreso), true);
        assertEquals(nuevoEgreso.getEstado(), EstadoEgreso.PENDIENTE);
    }

    @Test
    @DisplayName("Se crea un egreso (que no requiere presupuesto) de manera definitiva: queda aceptado y no se agrega al validador")
    public void CrearEgresoDefinitivoQueNoRequierePresupuesto() {
        creadorEgreso.agregarPresupuesto(presupuesto2);
        Egreso nuevoEgreso = creadorEgreso.crearEgreso();
        assertEquals(validador.getEgresosPendientes().contains(nuevoEgreso), false);
        assertEquals(nuevoEgreso.getEstado(), EstadoEgreso.ACEPTADO);
    }

}


