package dds.helpers;

import dds.exception.PasswordException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.List;

public class LectorArchivos {

    private String pathArchivo;
    private BufferedReader bufferReader;

    public LectorArchivos(String pathArchivo){
        this.pathArchivo = pathArchivo;
        try{
            this.bufferReader = new BufferedReader(new FileReader(pathArchivo));
        }
        catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<String> devolverContenidoComoListaDeStrings() {

        String resultado = "";
        try{
            String bfRead;
            while ((bfRead = bufferReader.readLine()) != null) {
                resultado = resultado + bfRead + ",";
            }

            bufferReader.close();

        }
        catch(IOException ex){
            System.err.println(ex.getMessage());
        }

        return Arrays.asList(resultado.split(","));
    }

}
