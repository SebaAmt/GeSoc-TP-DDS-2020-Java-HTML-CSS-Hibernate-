package model.validaciones;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeContrasenias {
    private List<Validacion> validaciones = new ArrayList<>();

    public void agregarValidacion(Validacion validacion){
        this.validaciones.add(validacion);
    }

    public void validarContrasenia(String username, String password){
        this.validaciones.forEach(validacion -> validacion.validar(username, password));
    }
}
