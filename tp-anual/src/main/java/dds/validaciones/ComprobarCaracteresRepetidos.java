package dds.validaciones;

public class ComprobarCaracteresRepetidos extends Validacion {

    public ComprobarCaracteresRepetidos(){
        super("Hay caracteres repetidos!");
    }

    @Override
    public boolean condicion(String username, String password){
        boolean esRepetido = false;

        for (int i = 0; i < password.length() - 1; i++) {
            int valor1 = password.charAt(i);
            int valor2 = password.charAt(i + 1);

            if (valor1 == valor2)
                esRepetido = true;
        }

        return esRepetido;
    }
}
