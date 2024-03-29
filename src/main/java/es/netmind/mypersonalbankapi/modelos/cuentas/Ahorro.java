package es.netmind.mypersonalbankapi.modelos.cuentas;

import java.time.LocalDate;
import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class Ahorro extends Cuenta {

    public Ahorro(Integer id, LocalDate fechaCreacion, Double saldo, Double interes, Double comision) {
        super(id, fechaCreacion, saldo, interes, comision);
    }

    public boolean validar() {
        return this.validarComun();
    }

    public Double calcularRentabilidad(){
        return Double.valueOf(0);
    }
}
