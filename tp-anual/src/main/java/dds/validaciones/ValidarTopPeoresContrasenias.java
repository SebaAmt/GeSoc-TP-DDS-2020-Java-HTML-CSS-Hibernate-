package dds.validaciones;

import dds.exception.PasswordException;
import dds.helpers.LectorArchivos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ValidarTopPeoresContrasenias implements Validacion{
    private LectorArchivos lectorArchivos = new LectorArchivos("src\\main\\resources\\10k-most-common.txt");
    private List<String> contrasenias = lectorArchivos.devolverContenidoComoListaDeStrings();

    public ValidarTopPeoresContrasenias() {
    }

    // Verifica mediante un archivo txt de 10k peores contrasenias
    @Override
    public void validar(String username, String password) {
        if (contrasenias.stream().anyMatch(contrasenia -> contrasenia.equals(password))) {
            throw new PasswordException("La Password es debil");
        }
    }

}
