package dds.batch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.direccion.Direccion;
import dds.documentoComercial.DocumentoComercial;
import dds.documentoComercial.TipoDocumentoComercial;
import dds.egreso.CreadorMoneda;
import dds.egreso.CriterioPresupuestoMenorValor;
import dds.egreso.CriterioSeleccionPresupuesto;
import dds.egreso.CurrencyID;
import dds.egreso.Egreso;
import dds.egreso.Item;
import dds.egreso.Presupuesto;
import dds.egreso.Proveedor;
import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.entidades.Organizacion;
import dds.mediosDePago.MedioDePago;
import dds.mediosDePago.TipoMedioDePago;
import dds.egreso.Moneda;
import dds.usuario.TipoUsuario;
import dds.usuario.Usuario;
import dds.validacionesEgresos.EgresoCoincideConPresupuestoSeleccionadoPorCriterio;
import dds.validacionesEgresos.EgresoTieneCantidadMinimaDePresupuestos;
import dds.validacionesEgresos.ValidacionEgreso;

public class Main {

	public static Presupuesto presupuesto1;
	public static Presupuesto presupuesto2;
	public static Presupuesto presupuesto3;
	public static Egreso egreso1;
	public static Egreso egreso2;
	public static Item item1;
	public static Item item2;
	public static Item item3;
	public static Item item4;
	public static Item item5;
	public static Item item6;
	public static Item item7;
	public static Proveedor proveedor1;
	public static Proveedor proveedor2;
	public static DocumentoComercial documento1;
	public static DocumentoComercial documento2;
	public static MedioDePago medioDePago1;
	public static MedioDePago medioDePago2;
	public static List<Item> items1 = new ArrayList<>();
	public static List<Item> items2 = new ArrayList<>();
	public static List<Item> items3 = new ArrayList<>();
	public static List<Egreso> egresosPendientes1 = new ArrayList<>();;
	public static List<ValidacionEgreso> validaciones1 = new ArrayList<>();;
	public static List<Egreso> egresosPendientes2 = new ArrayList<>();;
	public static List<ValidacionEgreso> validaciones2 = new ArrayList<>();;
	public static CriterioSeleccionPresupuesto criterio1;
	public static List<Usuario> usuariosRevisores1 = new ArrayList<>();
	public static List<Usuario> usuariosRevisores2 = new ArrayList<>();
	public static List<Presupuesto> presupuestos1 = new ArrayList<>();;
	public static List<Presupuesto> presupuestos2 = new ArrayList<>();;
	public static Usuario usuario1;
	public static Usuario usuario2;
	public static Usuario usuario3;
	public static CreadorMoneda creadorPesos;
	public static Moneda peso;
	public static EntidadBase entidadBase1;
	public static EntidadBase entidadBase2;
	public static EntidadJuridica entidadJuridica1;
	public static Organizacion organizacion1;
	public static Direccion direccion1;
	public static Direccion direccion2;
	

	public static void main(String[] args) {

		initBatch();
		System.out.println("Validando egresos...");
		List<Egreso> egresosAValidar = organizacion1.obtenerEgresosParaValidar();
		organizacion1.validarEgresos();
		imprimirResultadoDeValidacion(egresosAValidar);
		System.out.println("La validación de egresos finalizó.");

	}

	private static void initBatch() {
		System.out.println("Inicializando datos de prueba...");

		direccion1 = new Direccion
				.DireccionBuilder()
				.calleBuild("Av. Cabildo")
				.alturaBuild(190)
				.pisoBuild(10)
				.departamentoBuild("D")
				.direccionPostalBuilder("1420")
				.buildDireccion();

		direccion2 = new Direccion
				.DireccionBuilder()
				.calleBuild("Av. Rivadavia")
				.alturaBuild(4400)
				.pisoBuild(null)
				.departamentoBuild(null)
				.direccionPostalBuilder("1406")
				.buildDireccion();
		
		proveedor1 = new Proveedor("Telas SA", 30258741, direccion1);
		proveedor2 = new Proveedor("Edenor", 40987654, direccion2);

		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();

		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
		item4 = new Item("Jabon en polvo", new BigDecimal(500), 1);
		item5 = new Item("Rollo tela", new BigDecimal(200), 3);
		item6 = new Item("Lamina de cuero", new BigDecimal(350), 4);
		item7 = new Item("Botella de tintura", new BigDecimal(70), 1);

		items1.add(item1);
		items1.add(item2);
		items1.add(item3);
		items2.add(item5);
		items2.add(item6);
		items2.add(item7);

		egresosPendientes1 = new ArrayList<>();
		egresosPendientes2 = new ArrayList<>();
		validaciones1 = new ArrayList<>();
		validaciones2 = new ArrayList<>();
		validaciones1.add(new EgresoTieneCantidadMinimaDePresupuestos());
		validaciones1.add(new EgresoCoincideConPresupuestoSeleccionadoPorCriterio());
		validaciones2.add(new EgresoTieneCantidadMinimaDePresupuestos());

		usuario1 = new Usuario("Pablo", "pablok421", TipoUsuario.ESTANDAR);
		usuario2 = new Usuario("Valeria42", "valeria565", TipoUsuario.ADMINISTRADOR);
		usuario3 = new Usuario("Juan10", "juanchi23", TipoUsuario.ESTANDAR);

		usuariosRevisores1.add(usuario1);
		usuariosRevisores1.add(usuario2);
		usuariosRevisores2.add(usuario1);
		usuariosRevisores2.add(usuario2);
		usuariosRevisores2.add(usuario3);

		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 0000001);
		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 9999999);

		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
		medioDePago2 = new MedioDePago(TipoMedioDePago.DINERO_EN_CUENTA, "456852159");

		criterio1 = new CriterioPresupuestoMenorValor();

		presupuesto1 = new Presupuesto(proveedor1, documento1, peso, items1);
		presupuesto2 = new Presupuesto(proveedor2, documento2, peso, items2);
		presupuesto3 = new Presupuesto(proveedor1, documento2, peso, items2);

		presupuestos1.add(presupuesto1);
		presupuestos1.add(presupuesto2);
		presupuestos2.add(presupuesto1);
		presupuestos2.add(presupuesto2);
		presupuestos2.add(presupuesto3);

		egreso1 = new Egreso(LocalDate.of(2020, 9, 12), proveedor1, documento1, medioDePago1, peso, items2,
				usuariosRevisores2, presupuestos2, true, criterio1);

		egreso2 = new Egreso(LocalDate.of(2020, 7, 15), proveedor2, documento1, medioDePago2, peso, items2,
				usuariosRevisores1, presupuestos1, true, criterio1);

		entidadBase1 = new EntidadBase("IT Hardware SA", "Entidad de prueba");
		entidadBase2 = new EntidadBase("Plásticos SA", "Otra Entidad de prueba");
		entidadJuridica1 = new EntidadJuridica("Software Solutions SRL", "S&S", "38421526123", "Av. San Juan 1056",
				"C1063ACD");

		entidadBase1.nuevoEgreso(egreso1);
		entidadBase2.nuevoEgreso(egreso2);
		entidadJuridica1.nuevoEgreso(egreso1);
		entidadJuridica1.nuevoEgreso(egreso2);
		entidadJuridica1.agregarEntidadBase(entidadBase1);
		entidadJuridica1.agregarEntidadBase(entidadBase2);

		organizacion1 = new Organizacion("Pablo Giménez Enterprises");
		organizacion1.agregarEntidadBase(entidadBase1);
		organizacion1.agregarEntidadJuridica(entidadJuridica1);
		organizacion1.agregarValidacionEgreso(new EgresoCoincideConPresupuestoSeleccionadoPorCriterio());
		organizacion1.agregarValidacionEgreso(new EgresoTieneCantidadMinimaDePresupuestos());

		System.out.println("Datos de prueba inicializados.");
	}

	private static void imprimirResultadoDeValidacion(List<Egreso> egresos) {
		for (Egreso egreso : egresos) {

			System.out.println("Egreso validado:" + " " + "Fecha de egreso:" + " "
					+ egreso.getFechaDeOperacion().toString() + " " + "Estado:" + " " + egreso.getEstado().toString());
		}
	}

}
