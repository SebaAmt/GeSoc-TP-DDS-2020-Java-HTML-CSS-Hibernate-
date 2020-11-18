package repositorios;

import model.egreso.Item;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import java.util.List;

public class RepositorioItems implements WithGlobalEntityManager {
    public static RepositorioItems instancia = new RepositorioItems();

    public void agregarItem (Item item){
        entityManager().persist(item);
    }

    public void agregarListaItems(List<Item> items){
        items.stream().forEach(i -> this.agregarItem(i));
    }
}
