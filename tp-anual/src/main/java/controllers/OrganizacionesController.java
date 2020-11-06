package controllers;

import model.entidades.EntidadBase;
import model.entidades.EntidadJuridica;
import model.entidades.Organizacion;
import model.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizacionesController implements WithGlobalEntityManager {

    public ModelAndView getOrganizaciones(Request request, Response response){
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("organizaciones", RepositorioOrganizaciones.instancia.obtenerOrganizacionPorUsuario(usuarioLogueado));

        return new ModelAndView(modelo, "organizaciones.html.hbs");
    }

    public ModelAndView getDetalleOrganizacion(Request request, Response response){
        String id = request.params(":id");
        SessionHelper.validarLogueado(request, response);
        Usuario usuarioLogueado = SessionHelper.getUsuarioLogueado(request);

        try{
            Organizacion organizacion = RepositorioOrganizaciones.instancia.obtenerOrganizacionPorId(Long.parseLong(id));
            if(organizacion == null)
                response.status(400);
            List<EntidadBase> entidadesBase = RepositorioEntidades.instancia.obtenerEntidadesBaseDeOrganizacion(Long.parseLong(id));
            List<EntidadJuridica> entidadesJuridicas = RepositorioEntidades.instancia.obtenerEntidadesJuridicasDeOrganizacion(Long.parseLong(id));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("organizacion", organizacion);
            modelo.put("entidadesBase", entidadesBase);
            modelo.put("entidadesJuridicas", entidadesJuridicas);

            return new ModelAndView(modelo, "detalle-organizacion.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
}
