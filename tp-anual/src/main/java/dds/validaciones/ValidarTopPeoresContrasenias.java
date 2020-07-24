package dds.validaciones;

import dds.helpers.LectorArchivos;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.google.common.io.Resources.getResource;

public class ValidarTopPeoresContrasenias extends Validacion {

    private List<String> contrasenias;

    public ValidarTopPeoresContrasenias() throws IOException {
        super("La Password es debil");

        URL urlArchivo = getResource("10k-most-common.txt");
        LectorArchivos lectorArchivos = new LectorArchivos(urlArchivo);

        contrasenias = lectorArchivos.devolverContenidoComoListaDeStrings();
    }

    // Verifica mediante un archivo txt de 10k peores contrasenias
    @Override
    public boolean condicion(String username, String password) {
        return contrasenias.stream().anyMatch(contrasenia -> contrasenia.equals(password));
    }

}