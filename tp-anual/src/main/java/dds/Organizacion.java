package dds;

import dds.entidades.EntidadBase;
import dds.entidades.EntidadJuridica;
import dds.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Organizacion {
    private String nombre;
    private List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();
    private List<EntidadJuridica> entidadesJuridicas = new ArrayList<EntidadJuridica>();
    private Usuario usuario;

    public Organizacion(String nombre) {
        this.nombre = nombre;
    }

    public void agregarEntidadBase(EntidadBase nuevaEntidadBase){
        this.entidadesBase.add(nuevaEntidadBase);
    }

    public void agregarEntidadJuridica(EntidadJuridica nuevaEntidadJuridica){
        this.entidadesJuridicas.add(nuevaEntidadJuridica);
    }

    public void asignarUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
}
