package controllers;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.entidades.Entidad;
import model.entidades.Organizacion;
import model.mediosDePago.MedioDePago;
import model.mediosDePago.TipoMedioDePago;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class EgresosController implements WithGlobalEntityManager {

    public ModelAndView getFormCreacionEgreso(Request request, Response response){
        String idOrg = request.params(":idOrg");
        String idEntidad = request.params(":idEntidad");
        try{
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));
            if(entidad == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idEntidad", Long.parseLong(idEntidad));
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("mediosDePago", TipoMedioDePago.values());
            modelo.put("etiquetasDisponibles", RepositorioOrganizaciones.instancia.obtenerEtiquetasDeOrganizacion(Long.parseLong(idOrg)));
            modelo.put("tiposDocumentoComercial", TipoDocumentoComercial.values());


            return new ModelAndView(modelo, "form-creacion-egreso.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }
}
