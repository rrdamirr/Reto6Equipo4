package es.netmind.mypersonalbankapi.persistencia.springData;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.modelos.cuentas.Ahorro;
import es.netmind.mypersonalbankapi.persistencia.ICuentasRepoData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import es.netmind.mypersonalbankapi.modelos.cuentas.Cuenta;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
public class CuentaRepoDataTest {

    @Autowired
    private ICuentasRepoData repoCuenta;

    @Test
    void getAll() {
         List<Cuenta> cuentas = repoCuenta.findAll();
        System.out.println("cuentas +++: " + cuentas);
        assertNotNull(cuentas);
    }

    @Test
    void getAccountById() {
        Optional<Cuenta> op = repoCuenta.findById(1);
        Cuenta aCuenta = op.get();
        System.out.println("aCuenta +++:"+aCuenta);
        assertEquals(aCuenta.getId(), 1);
        assertNotNull(aCuenta);
    }

    @Test
    void addAccount() throws Exception {
        Cuenta newCue = new Ahorro(null, LocalDate.now(), 5000.0, 1.5, 2.0);
        repoCuenta.save(newCue);
        Optional<Cuenta> op = repoCuenta.findById(newCue.getId());
        Cuenta aCuenta = op.get();
        assertEquals(aCuenta.getId(), newCue.getId());

    }

    @Test
    void deleteAccount() {
         Optional<Cuenta> op = repoCuenta.findById(6);
        Cuenta cue = op.get();
        repoCuenta.delete(cue);
        System.out.println("cue +++: " + cue);
        assertTrue(true);
    }

    @Test
    void updateAccount() {
        Optional<Cuenta> op = repoCuenta.findById(2);
        Cuenta cuUpd = op.get();

         Cuenta newCue = new Ahorro(cuUpd.getId(), LocalDate.now(), 1000.0, cuUpd.getInteres(), cuUpd.getComision());

        Cuenta cCambiado = repoCuenta.save(newCue);

        System.out.println("cCambiado +++: " + cCambiado);
        assertNotNull(newCue);
        assertEquals(newCue.getSaldo(), cCambiado.getSaldo());
    }

    @Test
    @Transactional
    void findByCliente_Id() {
        List<Cuenta> cuentas = repoCuenta.findByCliente_Id(1);

        for (Cuenta cprint: cuentas) {
            System.out.println("Cuentas: ++++++" + cprint.getId());
        }

        assertThat(cuentas.size(), greaterThan(0));
    }

    @Test
    @Transactional
    void findByCliente_IdAndId() {

        Cuenta nCuenta = repoCuenta.findByCliente_IdAndId(1, 3);
        System.out.println("Cliente: " + nCuenta.getCliente().getId() + " /Cuenta: +++++ " + nCuenta.getId());
        assertNotNull(nCuenta.getId());

    }
}
