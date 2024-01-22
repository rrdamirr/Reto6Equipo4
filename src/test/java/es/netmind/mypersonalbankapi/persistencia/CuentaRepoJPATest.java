package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.modelos.cuentas.Ahorro;
import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class CuentaRepoJPATest {

    @Autowired
    private ICuentasRepo repoCuenta;

    @Test
    void getAll() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void addAccount() throws Exception {
        Cuenta newCue = new Ahorro(null, LocalDate.now(), 5000.0, 1.5, 2.0);
        repoCuenta.addAccount(newCue);
        Cuenta aCuenta = repoCuenta.getAccountById(newCue.getId());
        assertEquals(aCuenta.getId(), newCue.getId());

    }

    @Test
    void deleteAccount() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void getAccountsByClient() {
    }

    @Test
    void getAccountsByClientAndId() {
    }
}