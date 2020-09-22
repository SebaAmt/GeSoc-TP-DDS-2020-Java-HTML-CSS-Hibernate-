package dds.validacionesEgresos;

import dds.egreso.Egreso;

import javax.persistence.*;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Table(name = "validaciones_egresos")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class ValidacionEgreso {

    @Id
    @GeneratedValue
    private long id;

    public abstract void validar(Egreso egreso);
}

