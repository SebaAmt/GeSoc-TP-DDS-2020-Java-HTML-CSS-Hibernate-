package repositorios;

import model.egreso.Moneda;
import model.mediosDePago.MedioDePago;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioMonedas implements WithGlobalEntityManager {

    public static RepositorioMonedas instancia = new RepositorioMonedas();

    public List<Moneda> obtenerMonedas(){
        return entityManager()
                .createQuery("from Moneda", Moneda.class) //
                .getResultList();
    }

    public Moneda obtenerMonedaPorId(String id){
        return entityManager().find(Moneda.class, id);
    }

}
