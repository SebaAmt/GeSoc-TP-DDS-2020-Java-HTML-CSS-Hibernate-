package dds.validaciones;

import dds.exception.PasswordException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ValidarTopPeoresContrasenias implements Validacion{

    // Verifica mediante un archivo txt de 10k peores contrasenias
    @Override
    public void validar(String username, String password) throws PasswordException {
        List<String> contrasenias = TransformarTxtAListaDeContrasenias("src\\main\\resources\\10k-most-common.txt");

        boolean isExist = contrasenias.stream().anyMatch(contrasenia -> contrasenia.equals(password));
        if (isExist) {
            throw new PasswordException("La Password es debil");
        }
    }

    private List<String> TransformarTxtAListaDeContrasenias(String direccion) {
        String texto = "";
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(direccion));
            String bfRead;
            String temp = "";
            while ((bfRead = buffer.readLine()) != null) {
                temp = temp + bfRead + ",";
            }

            texto = temp;

            buffer.close();

        } catch (Exception e) {
            System.err.println("No se encontro archivo");
        }
        return Arrays.asList(texto.split(","));
    }
}
