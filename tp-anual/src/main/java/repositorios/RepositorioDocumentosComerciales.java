package repositorios;

import model.documentoComercial.DocumentoComercial;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioDocumentosComerciales implements WithGlobalEntityManager {
    public static RepositorioDocumentosComerciales instancia = new RepositorioDocumentosComerciales();

    public void agregarDocumentoComercial(DocumentoComercial doc){
        entityManager().persist(doc);
    }
}
