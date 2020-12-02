package controllers;

import controllers.HomeController;
import org.eclipse.jetty.http.HttpStatus;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import spark.Request;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.lang.ref.ReferenceQueue;
import java.util.Map;
import java.util.Set;

import static spark.Spark.*;

public class Routes {

    public static void main(String[] args) {
        System.out.println("Iniciando servidor");

        Spark.port(8080);
        Spark.staticFileLocation("/public");

        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        UsuariosController usuariosController = new UsuariosController();
        HomeController homeController = new HomeController();
        OrganizacionesController organizacionesController = new OrganizacionesController();
        EntidadesController entidadesController = new EntidadesController();
        EgresosController egresosController = new EgresosController();
        ItemsController itemsController = new ItemsController();
        PresupuestosController presupuestosController = new PresupuestosController();

        before((request, response) -> {
            if(request.uri().equals("/login") || request.uri().equals("/main.css"))
                return;
            String username = request.session().attribute("userName");
            SessionHelper.validarLogueado(request, response);
        });

        before("/organizaciones/:idOrg", (request,response) -> {
            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(request.params(":idOrg")));
        });

        before("/organizaciones/:idOrg/*", (request,response) -> {
            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(request.params(":idOrg")));
        });

        //Login
        Spark.get("/login", (request, response) -> usuariosController.getFormularioLogin(request, response), engine);
        Spark.post("/login", (request, response) -> usuariosController.iniciarSesion(request, response));
        Spark.get("/cerrarSesion", (request, response) -> usuariosController.cerrarSesion(request, response));

        //Home
        Spark.get("/", (request, response) -> homeController.getHome(request, response), engine);

        //Organizaciones
        //Spark.get("/organizaciones", (request, response) -> organizacionesController.getOrganizaciones(request, response), engine);
        Spark.get("/organizaciones/:idOrg", (request, response) -> organizacionesController.getDetalleOrganizacion(request, response), engine);
        //Mensajes
        Spark.get("/mensajes", (request, response) -> usuariosController.getMensajes(request, response), engine);

        //Entidades
        Spark.get("/organizaciones/:idOrg/entidades/base/nueva", (request, response) -> entidadesController.getFormCreacionEntidadBase(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/base", (request, response) -> entidadesController.createEntidadBase(request, response), engine);
        Spark.get("/organizaciones/:idOrg/entidades/base/:idEntidad", (request, response) -> entidadesController.getDetalleEntidadBase(request, response), engine);
        Spark.get("/organizaciones/:idOrg/entidades/juridica/nueva", (request, response) -> entidadesController.getFormCreacionEntidadJuridica(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/juridica", (request, response) -> entidadesController.createEntidadJuridica(request, response), engine);
        Spark.get("/organizaciones/:idOrg/entidades/juridica/:idEntidad", (request, response) -> entidadesController.getDetalleEntidadJuridica(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad", (request, response) ->entidadesController.modificarCategoria(request, response));
        
        //Egresos
        Spark.get("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/nuevo", egresosController::getFormCreacionEgreso, engine);
        Spark.post("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos", (request, response) -> egresosController.crearEgreso(request, response));
        Spark.get("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso", (request, response) -> egresosController.getDetalleEgreso(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso/nuevoRevisor", (request, response) -> egresosController.agregarRevisor(request, response));

        //Items
        Spark.get("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso/items/nuevo", (request,response) -> itemsController.getFormCreacionItem(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso/items", (request,response) -> itemsController.crearItem(request, response));

        //Presupuestos
        Spark.get("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso/presupuestos/nuevo", (request,response) -> presupuestosController.getFormCreacionPresupuesto(request, response), engine);
        Spark.post("/organizaciones/:idOrg/entidades/:tipoEntidad/:idEntidad/egresos/:idEgreso/presupuestos", (request,response) -> presupuestosController.crearPresupuesto(request, response));


        // FORO
        after((request, response) -> {
            PerThreadEntityManagers.getEntityManager();
            PerThreadEntityManagers.closeEntityManager();
        });
    }
}
