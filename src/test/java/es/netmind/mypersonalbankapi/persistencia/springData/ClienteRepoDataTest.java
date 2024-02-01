package es.netmind.mypersonalbankapi.persistencia.springData;
import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.modelos.clientes.Empresa;
import es.netmind.mypersonalbankapi.modelos.clientes.Personal;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepoData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@EnableAutoConfiguration
class ClienteRepoDataTest {

    @Autowired
    private IClientesRepoData repoCliente;

    @Test
    void getAll() throws Exception {

        List<Cliente> clientes = repoCliente.findAll();
        System.out.println("Clientes +++: " +clientes);
        assertNotNull(clientes);

    }

    @Test
    void getClientById() throws Exception {
        Optional<Cliente> op = repoCliente.findById(1);
        Cliente aCliente = op.get();
        System.out.println("aCliente +++:"+aCliente);
        assertEquals(aCliente.getId(), 1);
        assertNotNull(aCliente);
    }


    @ParameterizedTest
    @CsvSource({"Personal,Equipo4_Nuevo2,e4@gmail.com,Calle cuatro,12345678Z", "Empresa2,Empresa4 SA,emp4@gmail.com,Calle Empresa cuatro ,B12345678"})
    void addClient(String tipoCliente, String nombre, String mail, String direccion, String idDocumento) throws Exception {

        Cliente newCl = null;

        if (Objects.equals(tipoCliente, "Personal")) {
            newCl = new Personal(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento);
        } else {
            newCl = new Empresa(null, nombre, mail, direccion, LocalDate.now(), true, false, idDocumento, null );
        }
        System.out.println("cliente +++: " + newCl);
        repoCliente.save(newCl);
        Optional<Cliente> op = repoCliente.findById(newCl.getId());
        Cliente aCliente = op.get();
        assertEquals(aCliente.getId(), newCl.getId());

    }

    @Test
    void updateClient() throws Exception {
        Optional<Cliente> op = repoCliente.findById(2);
        Cliente clUpd = op.get();
        boolean TipoPersonal = clUpd instanceof Personal;

        Cliente newCl = null;
        if (TipoPersonal) {
            newCl = new Personal(clUpd.getId(), "3Fantasticos", clUpd.getEmail(), "La calle de la Risa", LocalDate.now(), true, false, ((Personal) clUpd).getDni());
        } else {
            newCl = new Empresa(clUpd.getId(), "Fantastic3 S.L.", clUpd.getEmail(), "La calle empresarial", LocalDate.now(), true, false, ((Empresa) clUpd).getCif(), null );
        }

        Cliente clCambiado = repoCliente.save(newCl);

        System.out.println("clCambiado +++: " + clCambiado);
        assertNotNull(newCl);
        assertEquals(newCl.getNombre(), clCambiado.getNombre());
        assertEquals(newCl.getDireccion(), clCambiado.getDireccion());
    }

    @Test
    void deleteClient() {
        Optional<Cliente> op = repoCliente.findById(3);
        Cliente cli = op.get();
        repoCliente.delete(cli);
        System.out.println("cli +++: " + cli);
        assertTrue(true);
    }


}
