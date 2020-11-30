package controllers;

import model.documentoComercial.DocumentoComercial;
import model.documentoComercial.TipoDocumentoComercial;
import model.egreso.*;
import model.entidades.Entidad;
import model.mediosDePago.MedioDePago;
import model.usuario.Usuario;
import org.eclipse.jetty.http.HttpStatus;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;
import repositorios.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ItemsController implements WithGlobalEntityManager, TransactionalOps {

    public ModelAndView getFormCreacionItem(Request request, Response response){
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

            return new ModelAndView(modelo, "form-creacion-item.html.hbs");
        }
        catch (Exception ex){
            response.status(HttpStatus.BAD_REQUEST_400);
            return null;
        }
    }

    public Void crearItem(Request request, Response response){
        String descripcion = request.queryParams("descripcion");
        BigDecimal precioUnitario = new BigDecimal(Double.parseDouble(request.queryParams("precioUnitario")));
        int cantidad = Integer.parseInt(request.queryParams("cantidad"));

        Item nuevoItem = new Item(descripcion, precioUnitario, cantidad);

        withTransaction(() ->{
            RepositorioItems.instancia.agregarItem(nuevoItem);
            Egreso egreso = RepositorioEgresos.instancia.getEgresoPorId(Long.parseLong(request.params(":idEgreso")));
            egreso.agregarItem(nuevoItem);
        });

        //response.redirect("/organizaciones/" + request.params(":idOrg") + "/entidades/" + request.params(":idEntidad") + "/egresos/" + request.params(":idEgreso"));
        //response.redirect("../" + request.params(":idEgreso"));
        response.redirect(request.uri().replace("/items", ""));
        return null;
    }
}
