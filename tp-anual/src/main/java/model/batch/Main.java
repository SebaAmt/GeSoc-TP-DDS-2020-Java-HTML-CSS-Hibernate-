package model.batch;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.direccion.Direccion;
import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.CreadorMoneda;
import model.egreso.CriterioSeleccionPresupuesto;
import model.egreso.CurrencyID;
import model.egreso.Egreso;
import model.egreso.Item;
import model.egreso.Presupuesto;
import model.egreso.Proveedor;
import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import model.egreso.Moneda;
import model.usuario.TipoUsuario;
import model.usuario.Usuario;
import model.validacionesEgresos.ValidacionEgreso;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

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
	public static Item item8;
	public static Item item9;
	public static Proveedor proveedor1;
	public static Proveedor proveedor2;
	public static Proveedor proveedor3;
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

	

	public static void main(String[] args) throws SchedulerException {

		initBatch();
		System.out.println("-- Datos de prueba inicializados --");

		List<Organizacion> organizaciones = new ArrayList<>();
		organizaciones.add(organizacion1);

		JobDetail validacionDeEgresos = JobBuilder.newJob(ValidacionEgresosJob.class).build();
		validacionDeEgresos.getJobDataMap().put("organizaciones", organizaciones);

		Trigger cada10Segundos = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.scheduleJob(validacionDeEgresos, cada10Segundos);

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
		proveedor3 = new Proveedor("Edesur", 458712452, direccion1);

		creadorPesos = new CreadorMoneda(CurrencyID.ARS);
		peso = creadorPesos.getMoneda();

		item1 = new Item("Rollo tela", new BigDecimal(300), 3);
		item2 = new Item("Lamina de cuero", new BigDecimal(300), 4);
		item3 = new Item("Botella de tintura", new BigDecimal(50), 1);
		item4 = new Item("Rollo tela", new BigDecimal(200), 3);
		item5 = new Item("Lamina de cuero", new BigDecimal(350), 4);
		item6 = new Item("Botella de tintura", new BigDecimal(70), 1);
		item7 = new Item("Rollo tela", new BigDecimal(220), 3);
		item8 = new Item("Lamina de cuero", new BigDecimal(400), 4);
		item9 = new Item("Botella de tintura", new BigDecimal(90), 1);

		items1.add(item1);
		items1.add(item2);
		items1.add(item3);

		items2.add(item4);
		items2.add(item5);
		items2.add(item6);

		items3.add(item7);
		items3.add(item8);
		items3.add(item9);

		egresosPendientes1 = new ArrayList<>();
		egresosPendientes2 = new ArrayList<>();
		validaciones1 = new ArrayList<>();
		validaciones2 = new ArrayList<>();
		validaciones1.add(ValidacionEgreso.CANTIDAD_MINIMA);
		validaciones1.add(ValidacionEgreso.COINCIDE_CON_CRITERIO);
		validaciones2.add(ValidacionEgreso.COINCIDE_CON_PRESUPUESTO_CARGADO);

		usuario1 = new Usuario("Pablo", "pablok421", TipoUsuario.ESTANDAR);
		usuario2 = new Usuario("Valeria42", "valeria565", TipoUsuario.ADMINISTRADOR);
		usuario3 = new Usuario("Juan10", "juanchi23", TipoUsuario.ESTANDAR);

		usuariosRevisores1.add(usuario1);
		usuariosRevisores1.add(usuario2);
		usuariosRevisores2.add(usuario1);
		usuariosRevisores2.add(usuario2);
		usuariosRevisores2.add(usuario3);

		documento1 = new DocumentoComercial(TipoDocumentoComercial.FACTURA, 1234);
		documento2 = new DocumentoComercial(TipoDocumentoComercial.ORDEN_DE_COMPRA, 7890);

		medioDePago1 = new MedioDePago(TipoMedioDePago.CAJERO, "159753456");
		medioDePago2 = new MedioDePago(TipoMedioDePago.DINERO_EN_CUENTA, "456852159");

		criterio1 = CriterioSeleccionPresupuesto.MENOR_VALOR;

		presupuesto1 = new Presupuesto(proveedor1, documento1, peso, items1);
		presupuesto2 = new Presupuesto(proveedor2, documento2, peso, items2);
		presupuesto3 = new Presupuesto(proveedor3, documento2, peso, items3);

		presupuestos1.add(presupuesto1);
		presupuestos1.add(presupuesto2);
		presupuestos2.add(presupuesto1);
		presupuestos2.add(presupuesto2);
		presupuestos2.add(presupuesto3);

		egreso1 = new Egreso(LocalDate.of(2020, 9, 12), proveedor1, documento1, medioDePago1, peso, items2,
				usuariosRevisores2, presupuestos2, true, null);

		egreso2 = new Egreso(LocalDate.of(2020, 7, 15), proveedor2, documento2, medioDePago2, peso, items2,
				usuariosRevisores1, presupuestos1, true, criterio1);

		entidadBase1 = new EntidadBase("IT Hardware SA", "Entidad de prueba");
		entidadBase2 = new EntidadBase("Plásticos SA", "Otra Entidad de prueba");
		entidadJuridica1 = new EntidadJuridica("Software Solutions SRL", "S&S", "38421526123", "Av. San Juan 1056",
				"C1063ACD");

		entidadBase1.nuevoEgreso(egreso1);
		entidadBase2.nuevoEgreso(egreso2);
		entidadJuridica1.agregarEntidadBase(entidadBase1);
		entidadJuridica1.agregarEntidadBase(entidadBase2);

		organizacion1 = new Organizacion("Pablo Giménez Enterprises");
		organizacion1.agregarEntidadJuridica(entidadJuridica1);
		organizacion1.agregarValidacionEgreso(ValidacionEgreso.COINCIDE_CON_CRITERIO);
		organizacion1.agregarValidacionEgreso(ValidacionEgreso.CANTIDAD_MINIMA);

		System.out.println("Datos de prueba inicializados.");
	}

	private static void imprimirResultadoDeValidacion(List<Egreso> egresos) {
		for (Egreso egreso : egresos) {
			System.out.println("Egreso validado: " + egreso.toString() + " | Resultado -> Estado:" + " " + egreso.getEstado().toString());

		}
	}

	public static class ValidacionEgresosJob implements Job {

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
			List<Organizacion> organizaciones = (ArrayList<Organizacion>)dataMap.get("organizaciones");
			System.out.println(">> INICIO VALIDACIÓN DE EGRESOS <<");
			organizaciones.stream().forEach(organizacion -> {
				System.out.println(organizacion);
				List<Egreso> egresosAValidar = organizacion.obtenerEgresosParaValidar();
				organizacion.validarEgresos();
				imprimirResultadoDeValidacion(egresosAValidar);
			});
			System.out.println(">> FIN VALIDACIÓN DE EGRESOS <<");
		}
	}

}
