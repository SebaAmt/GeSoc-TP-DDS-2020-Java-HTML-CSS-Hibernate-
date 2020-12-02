package model.batch;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioEgresos;
import repositorios.RepositorioOrganizaciones;
import repositorios.RepositorioUsuarios;

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

		List<Organizacion> organizaciones = RepositorioOrganizaciones.instancia.listar();

		JobDetail validacionDeEgresos = JobBuilder.newJob(ValidacionEgresosJob.class).build();
		validacionDeEgresos.getJobDataMap().put("organizaciones", organizaciones);

		Trigger cada10Segundos = TriggerBuilder.newTrigger().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.scheduleJob(validacionDeEgresos, cada10Segundos);

	}

	private static void imprimirResultadoDeValidacion(List<Egreso> egresos) {
		for (Egreso egreso : egresos) {
			System.out.println("Egreso validado: " + egreso.toString() + " | Resultado -> Estado:" + " " + egreso.getEstado().toString());

		}
	}

	public static class ValidacionEgresosJob implements Job, WithGlobalEntityManager, TransactionalOps {

		@Override
		public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
			JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
			List<Organizacion> organizaciones = (ArrayList<Organizacion>)dataMap.get("organizaciones");
			System.out.println(">> INICIO VALIDACIÓN DE EGRESOS <<");
			organizaciones.stream().forEach(organizacion -> {
				System.out.println(organizacion);
				List<Egreso> egresosAValidar = organizacion.obtenerEgresosParaValidar();
				List<Usuario> usuariosRevisores = egresosAValidar.stream().flatMap(e -> e.getRevisores().stream()).collect(Collectors.toList());

				withTransaction(() ->{
					organizacion.validarEgresos();
					RepositorioEgresos.instancia.actualizarEgresos(egresosAValidar);
					RepositorioUsuarios.instancia.actualizarUsuarios(usuariosRevisores);
				});
				imprimirResultadoDeValidacion(egresosAValidar);
			});
			System.out.println(">> FIN VALIDACIÓN DE EGRESOS <<");
		}
	}

}

