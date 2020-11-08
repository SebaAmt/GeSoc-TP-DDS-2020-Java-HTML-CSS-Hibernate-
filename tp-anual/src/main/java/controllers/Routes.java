package controllers;

import controllers.HomeController;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {

    public static void main(String[] args) {
        System.out.println("Iniciando servidor");

        Spark.port(8080);
        Spark.staticFileLocation("/resources/public");

        HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

        UsuariosController usuariosController = new UsuariosController();
        HomeController homeController = new HomeController();
        OrganizacionesController organizacionesController = new OrganizacionesController();
        EntidadesController entidadesController = new EntidadesController();
        EgresosController egresosController = new EgresosController();

        //Login
        Spark.get("/login", (request, response) -> usuariosController.getFormularioLogin(request, response), engine);
        Spark.post("/login", (request, response) -> usuariosController.iniciarSesion(request, response));

        //Home
        Spark.get("/", (request, response) -> homeController.getHome(request, response), engine);

        //Organizaciones
        //Spark.get("/organizaciones", (request, response) -> organizacionesController.getOrganizaciones(request, response), engine);
        Spark.get("/organizaciones/:id", (request, response) -> organizacionesController.getDetalleOrganizacion(request, response), engine);

        //Entidades
        Spark.get("/organizaciones/:idOrg/entidades/:idEntidad", (request, response) -> entidadesController.getDetalleEntidad(request, response), engine);

        //Egresos
        Spark.get("/organizaciones/:idOrg/entidades/:idEntidad/egresos/nuevo", egresosController::getFormCreacionEgreso, engine);
    }


}
