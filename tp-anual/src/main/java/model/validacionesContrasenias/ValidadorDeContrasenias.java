package model.validacionesContrasenias;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeContrasenias {
    private List<ValidacionContrasenia> validaciones = new ArrayList<>();

    public void agregarValidacion(ValidacionContrasenia validacionContrasenia){
        this.validaciones.add(validacionContrasenia);
    }

    public void validarContrasenia(String username, String password){
        this.validaciones.forEach(validacion -> validacion.validar(username, password));
    }
}
