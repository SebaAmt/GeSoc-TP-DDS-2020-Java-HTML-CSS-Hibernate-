package model.entidades.test;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import model.validacionesEgresos.ValidacionEgreso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizacionesTest {
    private Organizacion organizacion;
    private EntidadJuridica entidadJuridica;
    private EntidadBase entidadBase;
    private Usuario revisor1;
    private Usuario revisor2;
    private Egreso egresoPendiente;
    private Egreso egresoRechazado;
    private Egreso egresoAceptado;
    private Egreso egresoNoCorrespondienteAPresupuestos;
    private Egreso egresoConCriterioMenorValor;
    private List<Item> itemsCorrectos1 = new ArrayList<>();
    private List<Item> itemsCorrectos2 = new ArrayList<>();
    private List<Item> itemsCorrectos3 = new ArrayList<>();
    private List<Item> itemsIncorrectos1 = new ArrayList<>();
    private ValidacionEgreso cantidadMinimaPresupuestos = ValidacionEgreso.CANTIDAD_MINIMA;
    private ValidacionEgreso relacionEgresoPresupuesto = ValidacionEgreso.COINCIDE_CON_PRESUPUESTO_CARGADO;
    private ValidacionEgreso relacionEgresoPresupuestoSegunCriterio = ValidacionEgreso.COINCIDE_CON_CRITERIO;
    private Presupuesto presupuestoCorrecto1;
    private Presupuesto presupuestoCorrecto2;
    private Presupuesto presupuestoCorrecto3;
    private Proveedor proveedor;
    private CreadorMoneda creadorPesos;
    private Moneda moneda;
    
    @BeforeEach
    public void init() {

    	organizacion = new Organizacion("Organizacion de prueba");
        entidadJuridica = new EntidadJuridica("Razon Social test", "Entidad Juridica test", "123456789", "Direccion 123", "123");
        entidadBase = new EntidadBase("Entidad Base Test", "Una entidad para los test");
        organizacion.agregarEntidadBase(entidadBase);
        organizacion.agregarEntidadJuridica(entidadJuridica);

        organizacion.agregarValidacionEgreso(cantidadMinimaPresupuestos);
        organizacion.agregarValidacionEgreso(relacionEgresoPresupuesto);
        organizacion.agregarValidacionEgreso(relacionEgresoPresupuestoSegunCriterio);

        revisor1 = new Usuario("Pepe", "xtpz13l2", TipoUsuario.ESTANDAR);
        revisor2 = new Usuario("Juanita", "xwqponh302", TipoUsuario.ESTANDAR);
        List<Usuario> revisores = new ArrayList<>();
        revisores.add(revisor1);
        revisores.add(revisor2);

        proveedor = new Proveedor("Telas SA", 30258741, null);
        creadorPesos = new CreadorMoneda(CurrencyID.ARS);
        moneda = creadorPesos.getMoneda();
        DocumentoComercial factura = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
        MedioDePago efectivo = new MedioDePago(TipoMedioDePago.EFECTIVO, "PF12345");

        itemsCorrectos1.add(new Item("Martillo", new BigDecimal(30), 1));
        itemsCorrectos1.add(new Item("Clavos", new BigDecimal(5), 10));
        itemsCorrectos1.add(new Item("Madera", new BigDecimal(100), 5));

        egresoPendiente = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, itemsCorrectos1, revisores, new ArrayList<>(), true,null);
        egresoRechazado = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, itemsCorrectos1, new ArrayList<>(), new ArrayList<>(), true, null);
        egresoAceptado = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, itemsCorrectos1, new ArrayList<>(), new ArrayList<>(), false, null);

        presupuestoCorrecto1 = new Presupuesto(proveedor, factura, moneda, itemsCorrectos1);

        itemsCorrectos2.add(new Item("Madera", new BigDecimal(80), 5));
        itemsCorrectos2.add(new Item("Clavos", new BigDecimal(8), 10));
        itemsCorrectos2.add(new Item("Martillo", new BigDecimal(35), 1));

        presupuestoCorrecto2 = new Presupuesto(proveedor, factura, moneda, itemsCorrectos2);

        itemsCorrectos3.add(new Item("Madera", new BigDecimal(1), 5));
        itemsCorrectos3.add(new Item("Clavos", new BigDecimal(1), 10));
        itemsCorrectos3.add(new Item("Martillo", new BigDecimal(1), 1));

        presupuestoCorrecto3 = new Presupuesto(proveedor, factura, moneda, itemsCorrectos3);

        itemsIncorrectos1.add(new Item("Madera", new BigDecimal(54), 5));
        itemsIncorrectos1.add(new Item("Clavos", new BigDecimal(23), 10));
        itemsIncorrectos1.add(new Item("Martillo", new BigDecimal(33), 1));

        egresoNoCorrespondienteAPresupuestos = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, itemsIncorrectos1, revisores, new ArrayList<>(), true,null);

        egresoConCriterioMenorValor = new Egreso(LocalDate.now(), proveedor, factura, efectivo, moneda, itemsCorrectos1, revisores, new ArrayList<>(), true, CriterioSeleccionPresupuesto.MENOR_VALOR);
    }


    @Test
    @DisplayName("Se obtiene egreso para validar (PENDIENTE) de Entidad Jurídica")
    public void ObtenerEgresoPendienteEntidadJuridica() {
        entidadJuridica.nuevoEgreso(egresoPendiente);
        assertEquals(true, organizacion.obtenerEgresosParaValidar().contains(egresoPendiente));
    }

    @Test
    @DisplayName("Se obtiene egreso para validar (RECHAZADO) de Entidad Jurídica")
    public void ObtenerEgresoRechazadoEntidadJuridica() {
        entidadJuridica.nuevoEgreso(egresoRechazado);
        organizacion.validarEgresos();
        assertEquals(true, organizacion.obtenerEgresosParaValidar().contains(egresoRechazado));
    }

    @Test
    @DisplayName("Se obtiene egreso para validar (PENDIENTE) de Entidad Base")
    public void ObtenerEgresoPendienteEntidadBase() {
        entidadBase.nuevoEgreso(egresoPendiente);
        assertEquals(true, organizacion.obtenerEgresosParaValidar().contains(egresoPendiente));
    }

    @Test
    @DisplayName("Se obtiene egreso para validar (RECHAZADO) de Entidad Base")
    public void ObtenerEgresoRechazadoEntidadBase() {
        entidadBase.nuevoEgreso(egresoRechazado);
        organizacion.validarEgresos();
        assertEquals(true, organizacion.obtenerEgresosParaValidar().contains(egresoRechazado));
    }

    @Test
    @DisplayName("No se obtiene egreso de Entidad Jurídica para validar ya que está ACEPTADO")
    public void ObtenerEgresoAceptadoEntidadJuridica() {
        entidadJuridica.nuevoEgreso(egresoAceptado);
        assertEquals(false, organizacion.obtenerEgresosParaValidar().contains(egresoAceptado));
    }
    @Test
    @DisplayName("No se obtiene egreso de Entidad Base para validar ya que está ACEPTADO")
    public void ObtenerEgresoAceptadoEntidadBase() {
        entidadBase.nuevoEgreso(egresoAceptado);
        assertEquals(false, organizacion.obtenerEgresosParaValidar().contains(egresoAceptado));
    }

    @Test
    @DisplayName("Se valida egreso con presupuestos correctos y pasa estar ACEPTADO")
    public void ValidarEgresoConPresupuestosCorrectos() {
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto1);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto2);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoPendiente);
        organizacion.validarEgresos();
        assertEquals(EstadoEgreso.ACEPTADO, egresoPendiente.getEstado());
    }

    @Test
    @DisplayName("Se valida egreso con menos de la cantidad minima de presupuestos y pasa estar RECHAZADO")
    public void ValidarEgresoConMenosPresupuestosQueLosNecesarios() {
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto1);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto2);
        entidadJuridica.nuevoEgreso(egresoPendiente);
        organizacion.validarEgresos();
        assertEquals(EstadoEgreso.RECHAZADO, egresoPendiente.getEstado());
    }

    @Test
    @DisplayName("Se valida egreso que no corresponde a ninguno de sus presupuestos cargados (sin criterio) y pasa estar RECHAZADO")
    public void ValidarEgresoQueNoCorrespondeConNingunPresupuestoCargado() {
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto1);
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto2);
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoNoCorrespondienteAPresupuestos);
        organizacion.validarEgresos();
        assertEquals(EstadoEgreso.RECHAZADO, egresoNoCorrespondienteAPresupuestos.getEstado());
    }

    @Test
    @DisplayName("Se valida egreso que no corresponde al presupuesto según el criterio asignado y pasa estar RECHAZADO")
    public void ValidarEgresoQueNoCorrespondePresupuestoSegunCriterio() {
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto1);
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto2);
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoConCriterioMenorValor);
        organizacion.validarEgresos();
        assertEquals(EstadoEgreso.RECHAZADO, egresoConCriterioMenorValor.getEstado());
    }

    @Test
    @DisplayName("Se envía mensaje a Revisores por egreso ACEPTADO")
    public void ValidarMensajeEgresoAceptado() {
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto1);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto2);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoPendiente);
        organizacion.validarEgresos();
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue ACEPTADO", revisor1.getBandejaDeMensajes().get(0));
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue ACEPTADO", revisor2.getBandejaDeMensajes().get(0));
    }

    @Test
    @DisplayName("Se envía mensaje a Revisores por Egreso RECHAZADO por no cumplir cantidad mínima de presupuestos")
    public void ValidarMensajeEgresoRechazadoPorNoCumplirCantidadMinimaDePresupuestos() {
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto1);
        egresoPendiente.agregarPresupuesto(presupuestoCorrecto2);
        entidadJuridica.nuevoEgreso(egresoPendiente);
        organizacion.validarEgresos();
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no cumple con la cantidad minima de presupuestos cargados", revisor1.getBandejaDeMensajes().get(0));
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no cumple con la cantidad minima de presupuestos cargados", revisor2.getBandejaDeMensajes().get(0));
    }

    @Test
    @DisplayName("Se envía mensaje a Revisores por Egreso RECHAZADO por no correspondencia entre Egreso y Presupuestos (sin criterio)")
    public void ValidarMensajeEgresoRechazadoPorNoCorrespondenciaEntreEgresoYPresupuestosSinCriterio() {
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto1);
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto2);
        egresoNoCorrespondienteAPresupuestos.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoNoCorrespondienteAPresupuestos);
        organizacion.validarEgresos();
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no se corresponde con ninguno de los presupuestos cargados", revisor1.getBandejaDeMensajes().get(0));
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no se corresponde con ninguno de los presupuestos cargados", revisor2.getBandejaDeMensajes().get(0));
    }

    @Test
    @DisplayName("Se envía mensaje a Revisores por Egreso RECHAZADO por no correspondencia entre Egreso y Presupuestos (con criterio)")
    public void ValidarMensajeEgresoRechazadoPorNoCorrespondenciaConElSeleccionadoSegunCriterio() {
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto1);
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto2);
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoConCriterioMenorValor);
        organizacion.validarEgresos();
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no se corresponde con el presupuesto seleccionado por criterio", revisor1.getBandejaDeMensajes().get(0));
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no se corresponde con el presupuesto seleccionado por criterio", revisor2.getBandejaDeMensajes().get(0));
    }

    @Test
    @DisplayName("Se envía mensaje a revisores por Egreso RECHAZADO con múltiples errores")
    public void ValidarMensajeEgresoRechazadoConMultiplesErrores(){
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto2);
        egresoConCriterioMenorValor.agregarPresupuesto(presupuestoCorrecto3);
        entidadJuridica.nuevoEgreso(egresoConCriterioMenorValor);
        organizacion.validarEgresos();
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no cumple con la cantidad minima de presupuestos cargados, El egreso no se corresponde con ninguno de los presupuestos cargados, El egreso no se corresponde con el presupuesto seleccionado por criterio", revisor1.getBandejaDeMensajes().get(0));
        assertEquals("El Egreso " + egresoPendiente.toString() + " fue RECHAZADO: El egreso no cumple con la cantidad minima de presupuestos cargados, El egreso no se corresponde con ninguno de los presupuestos cargados, El egreso no se corresponde con el presupuesto seleccionado por criterio", revisor2.getBandejaDeMensajes().get(0));
    }

}
