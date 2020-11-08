package controllers;

import model.documentoComercial.TipoDocumentoComercial;
import model.entidades.Entidad;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.RepositorioEgresos;
import repositorios.RepositorioEntidades;
import repositorios.RepositorioProveedores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class PresupuestosController implements WithGlobalEntityManager, TransactionalOps {

    public ModelAndView getFormCreacionPresupuesto(Request request, Response response){
        String idOrg = request.params(":idOrg");
        String idEntidad = request.params(":idEntidad");
        String idEgreso = request.params(":idEgreso");
        try{
            Entidad entidad = RepositorioEntidades.instancia.obtenerEntidadPorId(Long.parseLong(idEntidad));
            if(entidad == null){
                response.redirect("/error", 404); // not found
            }

            SessionHelper.validarOrganizacionUsuarioLogueado(request, response, Long.parseLong(idOrg));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("idEntidad", Long.parseLong(idEntidad));
            modelo.put("idOrganizacion", Long.parseLong(idOrg));
            modelo.put("idEgreso", Long.parseLong(idEgreso));
            modelo.put("monedaEgreso", RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(idEgreso)).getMoneda());
            modelo.put("itemsEgreso", RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(idEgreso)).getItems());
            modelo.put("proveedores", RepositorioProveedores.instancia.obtenerProveedores());
            modelo.put("tiposDocumentoComercial", TipoDocumentoComercial.values());

            return new ModelAndView(modelo, "form-creacion-presupuesto.html.hbs");
        }
        catch (Exception ex){
            response.status(400);
            return null;
        }
    }



}
