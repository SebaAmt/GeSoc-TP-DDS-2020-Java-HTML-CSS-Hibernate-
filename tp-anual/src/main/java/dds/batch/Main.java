package dds.batch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorEgreso;
import dds.egreso.CreadorProveedor;
import dds.egreso.CriterioPresupuestoMenorValor;
import dds.egreso.CriterioSeleccionPresupuesto;
import dds.egreso.Egreso;
import dds.egreso.EstadoEgreso;
import dds.egreso.Item;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.pais.Moneda;
import dds.usuario.TipoUsuario;
import dds.usuario.Usuario;
import dds.validacionesEgresos.EgresoEnBaseAPresupuestoCorrecto;
import dds.validacionesEgresos.EgresoTieneCantidadMinimaDePresupuestos;
import dds.validacionesEgresos.ValidacionEgreso;
//import dds.validacionesEgresos.ValidadorEgresos;

public class Main {
//	public static Presupuesto presupuesto1;
//	public static Presupuesto presupuesto2;
//	public static Presupuesto presupuesto3;
//	public static Egreso egreso1;
//	public static Egreso egreso2;
//	public static Item item1;
//	public static Item item2;
//	public static Item item3;
//	public static Item item4;
//	public static Item item5;
//	public static Item item6;
//	public static Item item7;
//	public static Proveedor proveedor1;
//	public static Proveedor proveedor2;
//	public static DocumentoComercial documento1;
//	public static DocumentoComercial documento2;
//	public static MedioDePago medioDePago1;
//	public static MedioDePago medioDePago2;
//	public static List<Item> items1 = new ArrayList<>();
//	public static List<Item> items2 = new ArrayList<>();
//	public static List<Item> items3 = new ArrayList<>();
//	public static CreadorEgreso creadorEgreso1;
//	public static CreadorEgreso creadorEgreso2;
//	public static ValidadorEgresos validador1;
//	public static ValidadorEgresos validador2;
//	public static List<Egreso> egresosPendientes1;
//	public static List<ValidacionEgreso> validaciones1;
//	public static List<Egreso> egresosPendientes2;
//	public static List<ValidacionEgreso> validaciones2;
//	public static CriterioSeleccionPresupuesto criterio1;
//	public static Usuario usuario1;
//	public static Usuario usuario2;
//	public static CreadorProveedor creadorProveedor;
//
//	public static void main(String[] args) {
//		initBatch();
//		System.out.println("Validando Egresos del validador 1 (Egresos con cantidad mínima de presupuestos "
//				+ "y Egresos en base a presupuesto correcto)");
//		validador1.validarEgresosPendientes();
//		imprimirResultadoDeValidacion(validador1);
//		System.out.println("Validaciones del validador 1 terminadas.");
//		System.out.println("Validando Egresos del validador 2 (Egresos con cantidad mínima de presupuestos)");
//		validador2.validarEgresosPendientes();
//		imprimirResultadoDeValidacion(validador2);
//		System.out.println("Validaciones del validador 2 terminadas.");
//		System.out.println("Egreso rechazado por no tener el número mínimo de presupuestos.");
//
//	}
//
//	private static void initBatch() {
//		System.out.println("Inicializando datos de prueba.");
//
//		creadorProveedor = new CreadorProveedor();
//		proveedor1 = creadorProveedor.crearProveedor("Telas SA", 30258741, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkJFTDcyNTJa", "Av. Cabildo", 2000, 9, "A", "1379");
//		proveedor2 = creadorProveedor.crearProveedor("Edenor", 40987654, "TUxBUENBUGw3M2E1", "TUxBQ0NBUGZlZG1sYQ", "TUxBQkNBQjM4MDda", "Av Rivadavia", 4400, null, null,"8520");
//
//		Moneda moneda = proveedor1.getDireccionPostal().getPais().getMoneda();
//
//		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
//		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
//		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
//		item4 = new Item("Jabon en polvo", new BigDecimal(500), 1);
//		item5 = new Item("Rollo tela", new BigDecimal(200), 3);
//		item6 = new Item("Lamina de cuero", new BigDecimal(350), 4);
//		item7 = new Item("Botella de tintura", new BigDecimal(70), 1);
//
//		items1.add(item1);
//		items1.add(item2);
//		items1.add(item3);
//		items2.add(item5);
//		items2.add(item6);
//		items2.add(item7);
//
//		egresosPendientes1 = new ArrayList<>();
//		egresosPendientes2 = new ArrayList<>();
//		validaciones1 = new ArrayList<>();
//		validaciones2 = new ArrayList<>();
//		validaciones1.add(new EgresoTieneCantidadMinimaDePresupuestos());
//		validaciones1.add(new EgresoEnBaseAPresupuestoCorrecto());
//		validaciones2.add(new EgresoTieneCantidadMinimaDePresupuestos());
//
//		usuario1 = new Usuario("Pablo", "pablok421", TipoUsuario.ESTANDAR);
//		usuario2 = new Usuario("Valeria42", "valeria565", TipoUsuario.ADMINISTRADOR);
//
//		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
//		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 9999999);
//
//		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
//		medioDePago2 = new MedioDePago(TipoMedioDePago.DINERO_EN_CUENTA, "456852159");
//
//		validador1 = new ValidadorEgresos(egresosPendientes1, validaciones1);
//		validador2 = new ValidadorEgresos(egresosPendientes2, validaciones2);
//
//		criterio1 = new CriterioPresupuestoMenorValor();
//
//		presupuesto1 = new Presupuesto(proveedor1, documento1, items1);
//		presupuesto2 = new Presupuesto(proveedor2, documento2, items2);
//		presupuesto3 = new Presupuesto(proveedor1, documento2, items2);
//
//		creadorEgreso1 = new CreadorEgreso(LocalDate.of(2020, 5, 15), proveedor1, documento2, medioDePago1, items1,
//				validador1);
//		creadorEgreso1.agregarPresupuesto(presupuesto1);
//		creadorEgreso1.agregarPresupuesto(presupuesto2);
//		creadorEgreso1.agregarPresupuesto(presupuesto3);
//
//		creadorEgreso1.asignarCriterioSeleccionPresupuesto(criterio1);
//		creadorEgreso1.asignarUsuarioRevisor(usuario1);
//		creadorEgreso1.requierePresupuestos();
//
//		creadorEgreso2 = new CreadorEgreso(LocalDate.of(2020, 7, 15), proveedor2, documento1, medioDePago2, items2,
//				validador2);
//		creadorEgreso2.agregarPresupuesto(presupuesto2);
//		creadorEgreso2.asignarUsuarioRevisor(usuario2);
//		creadorEgreso2.requierePresupuestos();
//
//		egreso1 = creadorEgreso1.crearEgreso();
//		egreso2 = creadorEgreso2.crearEgreso();
//
//		System.out.println("Datos de prueba inicializados.");
//	}
//
//	private static void imprimirResultadoDeValidacion(ValidadorEgresos validador) {
//		for (Egreso egreso : validador.getEgresosPendientes()) {
//
//		System.out.println("Egreso validado:"+ " " + egreso.toString() + " " + "Estado:" + " " + egreso.getEstado().toString());
//		}
//	}
//

}
